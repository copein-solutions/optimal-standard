package com.optimal.standard.service;


import static com.optimal.standard.util.MapperUtils.toMaterialMapper;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.repository.MaterialRepository;
import com.optimal.standard.util.MapperUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaterialService {

  private final MaterialRepository materialRepository;

  public List<ResponseMaterialDTO> findAll() {
    return this.materialRepository
        .findAll()
        .stream()
        .map(MapperUtils::toResponseDTO)
        .toList();
  }

  public void saveMaterial(MaterialDTO request) {
    //TODO: falta validar de alguna forma que no pueda ingresar materiales repetidos
    this.materialRepository.save(toMaterialMapper(request));
  }

  public void updateMaterial(Long id, MaterialDTO request) {
    this.materialRepository
        .findById(id)
        .ifPresentOrElse(materialDatabase -> {
          Material material = toMaterialMapper(request);
          material.setId(id);
          this.materialRepository.save(material);
        }, () -> {
          throw new EntityNotFoundException("Material not found with ID: " + id);
        });
  }

  public MaterialDTO findById(Long id) {
    return this.materialRepository
        .findById(id)
        .map(MapperUtils::toResponseDTO)
        .orElse(null);
  }

}