package com.optimal.standard.util;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.Material;
import java.util.Set;
import java.util.stream.Collectors;

public interface MapperUtils {

  static MaterialDTO toDTO(Material material) {
    return MaterialDTO
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
        .applicationAreas(material
            .getApplicationAreas()
            .stream()
            .map(MapperUtils::toDTO)
            .collect(Collectors.toList()))
        .build();
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

  static ApplicationAreaDTO toDTO(ApplicationArea applicationArea) {
    return ApplicationAreaDTO
        .builder()
        .name(applicationArea.getName())
        .specification(applicationArea.getSpecification())
        .considerations(applicationArea.getConsiderations())
        .build();
  }

}
