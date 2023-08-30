package com.optimal.standard.service;


import static com.optimal.standard.util.MaterialMapperUtils.toMaterial;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.exception.BadRequestException;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.repository.MaterialRepository;
import com.optimal.standard.util.MaterialMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaterialService {

  private static final String MATERIAL_NOT_FOUND_MESSAGE = "Material not found with ID: ";

  private final MaterialRepository materialRepository;

  private final MaterialFileService materialFileService;

  private final GlobalVariableService globalVariableService;

  public List<MaterialDTO> findAll() {
    double quotationDollar = this.globalVariableService.getQuotationDollar();
    return this.materialRepository
        .findAllByDeletedFalse()
        .stream()
        .map(material -> MaterialMapperUtils.toMaterialDTO(material, quotationDollar))
        .toList();
  }

  public List<MaterialDTO> findAllByIds(List<Long> ids) {
    double quotationDollar = this.globalVariableService.getQuotationDollar();
    return emptyIfNull(this.materialRepository.findMaterialsByIdInAndDeletedFalse(ids))
        .stream()
        .map(material -> MaterialMapperUtils.toMaterialDTO(material, quotationDollar))
        .toList();
  }

  public void saveMaterial(MaterialDTO request) {
    //TODO: falta validar de alguna forma que no pueda ingresar materiales repetidos
    Material material = this.materialRepository.save(toMaterial(request));
    this.materialFileService.saveMaterialFileAndMoveTempFile(request.getFiles(), material);
  }

  public void updateMaterial(Long id, MaterialDTO request) {
    this.materialRepository
        .findById(id)
        .ifPresentOrElse(materialDatabase -> {
          Long materialId = materialDatabase.getId();
          this.materialFileService.processMaterialFiles(materialDatabase, request.getFiles());
          Material material = toMaterial(request);
          material.setId(materialId);
          material.setConstructionSystems(materialDatabase.getConstructionSystems());
          material.setMaterialFiles(materialDatabase.getMaterialFiles());
          this.materialRepository.save(material);
        }, () -> {
          throw new EntityNotFoundException(MATERIAL_NOT_FOUND_MESSAGE + id);
        });
  }

  public MaterialDTO findById(Long id) {
    double quotationDollar = this.globalVariableService.getQuotationDollar();
    return this.materialRepository
        .findByIdAndDeletedFalse(id)
        .map(material -> MaterialMapperUtils.toMaterialDTO(material, quotationDollar))
        .orElseThrow(() -> new EntityNotFoundException(MATERIAL_NOT_FOUND_MESSAGE + id));
  }

  public Material findMaterialById(Long id) {
    return this.findMaterialEntityById(id);
  }

  public Material findMaterialByIdWithoutException(Long id) {
    return this.materialRepository
        .findByIdAndDeletedFalse(id)
        .orElse(null);
  }

  public Material findMaterialEntityById(Long id) {
    return this.materialRepository
        .findByIdAndDeletedFalse(id)
        .orElseThrow(() -> new EntityNotFoundException(MATERIAL_NOT_FOUND_MESSAGE + id));
  }

  public List<MaterialDTO> findAllByType(String type) {
    double quotationDollar = this.globalVariableService.getQuotationDollar();
    return this.materialRepository
        .findAllByTypeAndDeletedFalse(type)
        .stream()
        .map(material -> MaterialMapperUtils.toMaterialDTO(material, quotationDollar))
        .collect(toList());
  }

  public void deleteMaterial(Long id) {
    Material material = this.findMaterialEntityById(id);
    if (isNotEmpty(material.getConstructionSystems())) {
      throw new BadRequestException("Deleting the Material: " + id + " is not possible due to references from a Construction System.");
    }
    this.materialRepository.markAsDeleted(id);
  }

}
