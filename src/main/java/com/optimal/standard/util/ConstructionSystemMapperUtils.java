package com.optimal.standard.util;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.ConstructionSystemMaterialDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.ConstructionSystem;
import java.util.List;

public interface ConstructionSystemMapperUtils {

  static ConstructionSystem toConstructionSystem(ConstructionSystemDTO constructionSystem, ApplicationArea applicationArea) {
    return ConstructionSystem
        .builder()
        .id(constructionSystem.getId())
        .totalConsumption(constructionSystem.getTotalConsumption())
        .layers(constructionSystem.getLayers())
        .applicationMode(constructionSystem.getApplicationMode())
        .cured(constructionSystem.isCured())
        .applicationArea(applicationArea)
        .baseConditions(constructionSystem.getBaseConditions())
        .supportConditions(constructionSystem.getSupportConditions())
        .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
        .materialAreaDescription(constructionSystem.getMaterialAreaDescription())
        .build();
  }

  static ResponseConstructionSystemDTO toResponseDTO(ConstructionSystem constructionSystem, double totalPrice,
      List<ConstructionSystemMaterialDTO> constructionSystemMaterials) {
    return ResponseConstructionSystemDTO
        .builder()
        .id(constructionSystem.getId())
        .totalConsumption(constructionSystem.getTotalConsumption())
        .layers(constructionSystem.getLayers())
        .applicationMode(constructionSystem.getApplicationMode())
        .cured(constructionSystem.isCured())
        .totalPrice(totalPrice)
        .baseConditions(constructionSystem.getBaseConditions())
        .supportConditions(constructionSystem.getSupportConditions())
        .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
        .materialAreaDescription(constructionSystem.getMaterialAreaDescription())
        .applicationArea(ApplicationAreaMapperUtils.toDTO(constructionSystem.getApplicationArea()))
        .materials(constructionSystemMaterials)
        .build();
  }

}
