package com.optimal.standard.util;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.Material;
import java.util.Set;
import java.util.stream.Collectors;

public interface MapperUtils {

  static ResponseMaterialDTO toResponseDTO(Material material) {
    ResponseMaterialDTO responseMaterialDTO = new ResponseMaterialDTO();
    responseMaterialDTO.setName(material.getName());
    responseMaterialDTO.setType(material.getType());
    responseMaterialDTO.setClassification(material.getClassification());
    responseMaterialDTO.setComposition(material.getComposition());
    responseMaterialDTO.setPrice(material.getPrice());
    responseMaterialDTO.setTotalConsumption(material.getTotalConsumption());
    responseMaterialDTO.setHandsNumber(material.getHandsNumber());
    responseMaterialDTO.setApplicationMode(material.getApplicationMode());
    responseMaterialDTO.setCured(material.isCured());
    responseMaterialDTO.setApplicationAreas(material
        .getApplicationAreas()
        .stream()
        .map(MapperUtils::toResponseDTO)
        .collect(Collectors.toList()));
    return responseMaterialDTO;
  }

  static Material toMaterialMapper(MaterialDTO material, Set<ApplicationArea> applicationAreas) {
    return Material
        .builder()
        .name(material.getName())
        .type(material.getType())
        .classification(material.getClassification())
        .composition(material.getComposition())
        .price(material.getPrice())
        .totalConsumption(material.getTotalConsumption())
        .handsNumber(material.getHandsNumber())
        .applicationMode(material.getApplicationMode())
        .cured(material.isCured())
        .applicationAreas(applicationAreas)
        .build();
  }

  static ApplicationAreaDTO toResponseDTO(ApplicationArea applicationArea) {
    return ApplicationAreaDTO
        .builder()
        .name(applicationArea.getName())
        .specification(applicationArea.getSpecification())
        .considerations(applicationArea.getConsiderations())
        .build();
  }

}
