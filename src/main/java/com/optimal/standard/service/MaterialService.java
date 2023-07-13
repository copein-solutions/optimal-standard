package com.optimal.standard.service;


import static com.optimal.standard.util.MaterialMapperUtils.toMaterial;
import static java.util.stream.Collectors.toList;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
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

  private static final String NOT_FOUND_MESSAGE = "Material not found with";

  private final MaterialRepository materialRepository;

  public List<ResponseMaterialDTO> findAll() {
    return this.materialRepository
        .findAll()
        .stream()
        .map(MaterialMapperUtils::toResponseDTO)
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
          throw new EntityNotFoundException(NOT_FOUND_MESSAGE + " ID: " + id);
        });
  }

  public MaterialDTO findById(Long id) {
    return this.materialRepository
        .findById(id)
        .map(MaterialMapperUtils::toResponseDTO)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE + " ID: " + id));
  }

  public List<MaterialDTO> findAllByType(String type) {
    return this.materialRepository
        .findAllByType(type)
        .stream()
        .map(MaterialMapperUtils::toResponseDTO)
        .collect(toList());
  }

}
