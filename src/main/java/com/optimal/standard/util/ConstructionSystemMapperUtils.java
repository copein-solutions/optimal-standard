package com.optimal.standard.util;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.persistence.model.ConstructionSystem;

public interface ConstructionSystemMapperUtils {

  static ConstructionSystem toConstructionSystem(ConstructionSystemDTO constructionSystem) {
    return ConstructionSystem
            .builder()
            .id(constructionSystem.getId())
            .totalConsumption(constructionSystem.getTotalConsumption())
            .layers(constructionSystem.getLayers())
            .applicationMode(constructionSystem.getApplicationMode())
            .cured(constructionSystem.isCured())
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
            .applicationAreaId(constructionSystem.getApplicationArea().getId())
            .applicationAreaName(constructionSystem.getApplicationArea().getName())
            .build();
  }

}
