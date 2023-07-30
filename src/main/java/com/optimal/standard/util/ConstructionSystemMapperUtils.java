package com.optimal.standard.util;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.TypeOfUseOfMaterial;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.ConstructionSystemMaterial;

public interface ConstructionSystemMapperUtils {

  static ConstructionSystem toConstructionSystem(ConstructionSystemDTO constructionSystem) {
    return ConstructionSystem
            .builder()
            .id(constructionSystem.getId())
            .totalConsumption(constructionSystem.getTotalConsumption())
            .layers(constructionSystem.getLayers())
            .applicationMode(constructionSystem.getApplicationMode())
            .cured(constructionSystem.isCured())
            .baseConditions(constructionSystem.getBaseConditions())
            .supportConditions(constructionSystem.getSupportConditions())
            .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
            .build();
  }

  static ConstructionSystemDTO toDTO(ConstructionSystem constructionSystem) {
    return ConstructionSystemDTO
            .builder()
            .id(constructionSystem.getId())
            .totalConsumption(constructionSystem.getTotalConsumption())
            .layers(constructionSystem.getLayers())
            .applicationMode(constructionSystem.getApplicationMode())
            .cured(constructionSystem.isCured())
            .baseConditions(constructionSystem.getBaseConditions())
            .supportConditions(constructionSystem.getSupportConditions())
            .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
            .applicationAreaId(constructionSystem.getApplicationArea().getId())
            .applicationAreaName(constructionSystem.getApplicationArea().getName())
            .build();
  }

  static TypeOfUseOfMaterial toTypeOfUseMaterial(ConstructionSystemMaterial constructionSystemMaterial) {
    return TypeOfUseOfMaterial
            .builder()
            .id(constructionSystemMaterial.getId())
            .typeOfUse(constructionSystemMaterial.getTypeOfUse())
            .coefficient(constructionSystemMaterial.getCoefficient())
            .coefficientDescription(constructionSystemMaterial.getCoefficientDescription())
            .materialDescription(constructionSystemMaterial.getMaterialDescription())
            .build();
  }
}
