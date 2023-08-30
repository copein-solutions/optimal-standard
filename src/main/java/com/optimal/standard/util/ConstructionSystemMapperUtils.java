package com.optimal.standard.util;

import static com.optimal.standard.util.MaterialMapperUtils.toMaterialDTO;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.ConstructionSystemMaterialDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import java.util.List;
import java.util.stream.Collectors;

public interface ConstructionSystemMapperUtils {

  static ConstructionSystem toConstructionSystem(ConstructionSystemDTO constructionSystem, ApplicationArea applicationArea) {
    return ConstructionSystem
        .builder()
        .id(constructionSystem.getId())
        .totalConsumption(constructionSystem.getTotalConsumption())
        .layers(constructionSystem.getLayers())
        .totalPrice(constructionSystem.getTotalPrice())
        .applicationMode(constructionSystem.getApplicationMode())
        .cured(constructionSystem.isCured())
        .applicationArea(applicationArea)
        .baseConditions(constructionSystem.getBaseConditions())
        .supportConditions(constructionSystem.getSupportConditions())
        .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
        .materialAreaDescription(constructionSystem.getMaterialAreaDescription())
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
        .applicationAreaId(constructionSystem
            .getApplicationArea()
            .getId())
        .applicationAreaName(constructionSystem
            .getApplicationArea()
            .getName())
        .build();
  }

  static ResponseConstructionSystemDTO toResponseDTO(ConstructionSystem constructionSystem, double quotationDollar) {
    return ResponseConstructionSystemDTO
        .builder()
        .id(constructionSystem.getId())
        .totalConsumption(constructionSystem.getTotalConsumption())
        .layers(constructionSystem.getLayers())
        .applicationMode(constructionSystem.getApplicationMode())
        .cured(constructionSystem.isCured())
        .totalPrice(constructionSystem.getTotalPrice())
        .baseConditions(constructionSystem.getBaseConditions())
        .supportConditions(constructionSystem.getSupportConditions())
        .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
        .materialAreaDescription(constructionSystem.getMaterialAreaDescription())
        .applicationArea(ApplicationAreaMapperUtils.toDTO(constructionSystem.getApplicationArea()))
        .materials(toConstructionSystemMaterials(constructionSystem.getConstructionSystemMaterials(), quotationDollar))
        .build();
  }

  static List<ConstructionSystemMaterialDTO> toConstructionSystemMaterials(List<ConstructionSystemMaterial> constructionSystemMaterials,
      double quotationDollar) {
    return constructionSystemMaterials
        .stream()
        .map(csm -> toConstructionSystemMaterial(csm, quotationDollar))
        .collect(Collectors.toList());
  }

  static ConstructionSystemMaterialDTO toConstructionSystemMaterial(ConstructionSystemMaterial constructionSystemMaterial,
      double quotationDollar) {
    return ConstructionSystemMaterialDTO
        .builder()
        .id(constructionSystemMaterial.getId())
        .typeOfUse(constructionSystemMaterial.getTypeOfUse())
        .material(toMaterialDTO(constructionSystemMaterial.getMaterial(), quotationDollar))
        .coefficient(constructionSystemMaterial.getCoefficient())
        .coefficientDescription(constructionSystemMaterial.getCoefficientDescription())
        .materialDescription(constructionSystemMaterial.getMaterialDescription())
        .build();
  }

}
