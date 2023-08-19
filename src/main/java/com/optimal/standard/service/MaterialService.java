package com.optimal.standard.service;


import static com.optimal.standard.persistence.model.MaterialTypeFiles.PERMANENT;
import static com.optimal.standard.persistence.model.MaterialTypeFiles.TEMP;
import static com.optimal.standard.util.MaterialMapperUtils.toMaterial;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.exception.BadRequestException;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.model.MaterialFiles;
import com.optimal.standard.persistence.repository.MaterialFileRepository;
import com.optimal.standard.persistence.repository.MaterialRepository;
import com.optimal.standard.service.files.LocalFilesService;
import com.optimal.standard.service.files.TempFile;
import com.optimal.standard.util.MaterialMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class MaterialService {

  private static final String NOT_FOUND_MESSAGE = "Material not found with ID: ";

  private final MaterialRepository materialRepository;

  private final LocalFilesService localFilesService;

  private final MaterialFileRepository materialFileRepository;

  public List<MaterialDTO> findAll() {
    return this.materialRepository
        .findAllByDeletedFalse()
        .stream()
        .map(MaterialMapperUtils::toMaterialDTO)
        .toList();
  }

  public List<MaterialDTO> findAllByIds(List<Long> ids) {
    return emptyIfNull(this.materialRepository.findMaterialsByIdInAndDeletedFalse(ids))
        .stream()
        .map(MaterialMapperUtils::toMaterialDTO)
        .toList();
  }

  public void saveMaterial(MaterialDTO request) {
    //TODO: falta validar de alguna forma que no pueda ingresar materiales repetidos
    this.materialRepository.save(toMaterial(request));
  }

  public void updateMaterial(Long id, MaterialDTO request) {

    this.materialRepository
        .findById(id)
        .ifPresentOrElse(materialDatabase -> {
          Long materialId = materialDatabase.getId();
          //this.confirmMaterialFiles(materialId, request.getFiles());
          Material material = toMaterial(request);
          material.setId(materialId);
          this.materialRepository.save(material);
        }, () -> {
          throw new EntityNotFoundException(NOT_FOUND_MESSAGE + id);
        });
  }

  private void confirmMaterialFiles(Long materialId, List<String> fileNames) {
    List<MaterialFiles> materialFiles =
        emptyIfNull(this.materialFileRepository.findAllByMaterialIdAndTypeAndNameIn(materialId, TEMP, fileNames))
            .stream()
            .peek(mf -> mf.setType(PERMANENT))
            .toList();
    if (isNotEmpty(materialFiles)) {
      this.materialFileRepository.saveAll(materialFiles);
    }
  }


  public MaterialDTO findById(Long id) {
    return this.materialRepository
        .findByIdAndDeletedFalse(id)
        .map(MaterialMapperUtils::toMaterialDTO)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
  }

  public Material findMaterialById(Long id) {
    return this.findMaterialEntityById(id);
  }

  private Material findMaterialEntityById(Long id) {
    return this.materialRepository
        .findByIdAndDeletedFalse(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + id));
  }

  public List<MaterialDTO> findAllByType(String type) {
    return this.materialRepository
        .findAllByTypeAndDeletedFalse(type)
        .stream()
        .map(MaterialMapperUtils::toMaterialDTO)
        .collect(toList());
  }

  public void deleteMaterial(Long id) {
    Material material = this.findMaterialEntityById(id);
    if (isNotEmpty(material.getConstructionSystems())) {
      throw new BadRequestException("Deleting the Material: " + id + " is not possible due to references from a Construction System.");
    }
    this.materialRepository.markAsDeleted(id);
  }

  public void saveFiles(Long materialId, MultipartFile files) {
    Long size = files.getSize();

    Material material = this.findMaterialEntityById(materialId);
    // me quedo con el nombre original del archivo y le agrego el _materialId_fechaActual
    // guardo el archivo temporal en el storage con el nombre nuevo
    if (nonNull(material)) {
      this.localFilesService.saveToTempFile(files);
      this.materialFileRepository.save(new MaterialFiles(files.getOriginalFilename(), files.getSize(), TEMP, material));
      // guardo en la base de datos dicho nombre con un type temp y el id del material
      // luego en el edit de material consulto si existe algun archivo temp que coincida con ese id de material y nombres
    } else {
      // guardo en la base de datos dicho nombre con un type temp y sin id de material (ya que no existe)
      this.localFilesService.saveToTempFile(files);
      this.materialFileRepository.save(new MaterialFiles(files.getOriginalFilename(), files.getSize(), TEMP));
      // luego en el save y en el edit de material consulto si existe algun archivo temp que coincida con el nombre generado y la fecha
      // si existe le cambio el type a permanent, le agrego el id del material y lo muevo de directorio a uploads.
    }


  }

  public void deleteFiles(Long materialId) {
    Material material = this.findMaterialEntityById(materialId);
    if (nonNull(material)) {

    }
  }

  public MaterialFiles getFile(Long fileId) {
    return this.materialFileRepository
        .findById(fileId)
        .orElse(null);
  }

  public List<String> listTempFiles(String localPath) {
    return this.localFilesService.listTempFiles(localPath);

  }

  public Stream<String> getLocalFileLines(String localPath) {
    return this.localFilesService.getLocalFileLines(new TempFile(new File(localPath)));
  }

}
