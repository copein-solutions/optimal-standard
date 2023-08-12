package com.optimal.standard.service;


import static com.optimal.standard.util.MaterialMapperUtils.toMaterial;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
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

  private static final String NOT_FOUND_MESSAGE = "Material not found with ID: ";

  private final MaterialRepository materialRepository;

  public List<ResponseMaterialDTO> findAll() {
    return this.materialRepository
        .findAllByDeletedFalse()
        .stream()
        .map(MaterialMapperUtils::toResponseDTO)
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
          Material material = toMaterial(request);
          material.setId(materialDatabase.getId());
          this.materialRepository.save(material);
        }, () -> {
          throw new EntityNotFoundException(NOT_FOUND_MESSAGE + id);
        });
  }

  public MaterialDTO findById(Long id) {
    return this.materialRepository
        .findByIdAndDeletedFalse(id)
        .map(MaterialMapperUtils::toResponseDTO)
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
        .map(MaterialMapperUtils::toResponseDTO)
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
