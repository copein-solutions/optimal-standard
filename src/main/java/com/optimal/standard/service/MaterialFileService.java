package com.optimal.standard.service;

import static com.optimal.standard.service.files.LocalFilesService.getFinalName;
import static com.optimal.standard.util.MaterialMapperUtils.toDTO;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.optimal.standard.dto.FilesDTO;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.model.MaterialFiles;
import com.optimal.standard.persistence.repository.MaterialFileRepository;
import com.optimal.standard.service.files.LocalFilesService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class MaterialFileService {

  private static final String FILES_NOT_FOUND_MESSAGE = "File not found with ID: ";

  private final MaterialFileRepository materialFileRepository;

  private final LocalFilesService localFilesService;

  public static List<Long> collectMaterialIds(List<FilesDTO> files) {
    return emptyIfNull(files)
        .stream()
        .map(FilesDTO::getId)
        .toList();
  }

  public MaterialFiles getFile(Long fileId) {
    return this.materialFileRepository
        .findById(fileId)
        .orElse(null);
  }

  private MaterialFiles findMaterialFilesById(Long id) {
    return this.materialFileRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(FILES_NOT_FOUND_MESSAGE + id));
  }

  public MaterialFiles save(MaterialFiles materialFiles, MultipartFile multipartFile) {
    this.localFilesService.saveFile(multipartFile);
    return this.materialFileRepository.save(materialFiles);
  }

  public FilesDTO saveFiles(Material material, MultipartFile multipartFile) {
    MaterialFiles materialFiles =
        new MaterialFiles(getFinalName(multipartFile.getOriginalFilename()), multipartFile.getSize(), multipartFile.getContentType(), true);

    if (nonNull(material)) {
      materialFiles.setMaterial(material);
    }
    this.localFilesService.saveFile(multipartFile);
    return toDTO(this.materialFileRepository.save(materialFiles));
  }

  public void delete(FilesDTO file, String fileName) {
    this.localFilesService.deleteFile(fileName);
    this.materialFileRepository.deleteById(file.getId());
  }

  public void processMaterialFiles(Material material, List<FilesDTO> files) {
    if (isNotEmpty(files)) {
      List<Long> fileIds = MaterialFileService.collectMaterialIds(files);
      List<MaterialFiles> materialFiles =
          emptyIfNull(this.materialFileRepository.findAllByTempTrueAndMaterialIdAndIdIn(material.getId(), fileIds))
              .stream()
              .peek(mf -> mf.setTemp(Boolean.FALSE))
              .toList();
      if (isNotEmpty(materialFiles)) {
        this.materialFileRepository.saveAll(materialFiles);
      }
    } else {
      material
          .getMaterialFiles()
          .forEach(file -> this.localFilesService.deleteFile(file.getName()));
      this.materialFileRepository.deleteAllByMaterialId(material.getId());
    }
  }

  public void saveMaterialFileAndMoveTempFile(List<FilesDTO> requestFiles, Material material) {
    List<Long> fileIds = collectMaterialIds(requestFiles);

    if (isNotEmpty(fileIds)) {
      List<MaterialFiles> files = this.materialFileRepository
          .findAllByTempTrueAndIdIn(fileIds)
          .stream()
          .peek(file -> {
            this.localFilesService.moveTempFile(file.getName());
            file.setMaterial(material);
            file.setTemp(Boolean.FALSE);
          })
          .toList();
      this.materialFileRepository.saveAll(files);
    }
  }

  public void deleteFiles(FilesDTO file) {
    if (nonNull(file)) {
      MaterialFiles materialFiles = this.findMaterialFilesById(file.getId());
      this.delete(file, materialFiles.getName());
    }
  }

}
