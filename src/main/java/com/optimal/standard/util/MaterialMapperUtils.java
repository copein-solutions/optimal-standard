package com.optimal.standard.util;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import com.optimal.standard.dto.CompositionDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.Material;
import java.util.stream.Collectors;

public interface MaterialMapperUtils {

  static ResponseMaterialDTO toResponseDTO(Material material) {
    ResponseMaterialDTO responseMaterialDTO = new ResponseMaterialDTO();
    responseMaterialDTO.setId(material.getId());
    responseMaterialDTO.setProduct(material.getProduct());
    responseMaterialDTO.setBrand(material.getBrand());
    responseMaterialDTO.setPresentationQuantity(material.getPresentationQuantity());
    responseMaterialDTO.setPresentationUnit(material.getPresentationUnit());
    responseMaterialDTO.setPresentationPrice(material.getPresentationPrice());
    responseMaterialDTO.setPriceDate(material.getPriceDate());
    responseMaterialDTO.setCurrency(material.getCurrency());
    responseMaterialDTO.setType(material.getType());
    responseMaterialDTO.setComponent(material.getComponent());
    responseMaterialDTO.setMinApplicableTemp(material.getMinApplicableTemp());
    responseMaterialDTO.setPotLife(material.getPotLife());
    responseMaterialDTO.setCompositions(emptyIfNull(material.getConstructionSystems())
        .stream()
        .map(MaterialMapperUtils::toResponseDTO)
        .collect(Collectors.toList()));
    return responseMaterialDTO;
  }

  static Material toMaterial(MaterialDTO material) {
    return Material
        .builder()
        .product(material.getProduct())
        .brand(material.getBrand())
        .presentationQuantity(material.getPresentationQuantity())
        .presentationUnit(material.getPresentationUnit())
        .presentationPrice(material.getPresentationPrice())
        .priceDate(material.getPriceDate())
        .currency(material.getCurrency())
        .type(material.getType())
        .component(material.getComponent())
        .minApplicableTemp(material.getMinApplicableTemp())
        .potLife(material.getPotLife())
        .build();
  }

  static CompositionDTO toResponseDTO(ConstructionSystem constructionSystem) {
    return CompositionDTO
        .builder()
        .classification(constructionSystem.getClassification())
        .totalConsumption(constructionSystem.getTotalConsumption())
        .layers(constructionSystem.getLayers())
        .applicationMode(constructionSystem.getApplicationMode())
        .cured(constructionSystem.isCured())
        .build();
  }

}
