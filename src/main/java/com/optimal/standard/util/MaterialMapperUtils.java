package com.optimal.standard.util;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.model.Material;

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
//    responseMaterialDTO.setCompositions(emptyIfNull(material.getConstructionSystems())
//        .stream()
//        .map(MaterialMapperUtils::toResponseDTO)
//        .collect(Collectors.toList()));
    return responseMaterialDTO;
  }

  static MaterialDTO toMaterialDTO(Material material) {
    MaterialDTO materialDTO = new MaterialDTO();
    materialDTO.setId(material.getId());
    materialDTO.setProduct(material.getProduct());
    materialDTO.setBrand(material.getBrand());
    materialDTO.setPresentationQuantity(material.getPresentationQuantity());
    materialDTO.setPresentationUnit(material.getPresentationUnit());
    materialDTO.setPresentationPrice(material.getPresentationPrice());
    materialDTO.setPriceDate(material.getPriceDate());
    materialDTO.setCurrency(material.getCurrency());
    materialDTO.setType(material.getType());
    materialDTO.setComponent(material.getComponent());
    materialDTO.setMinApplicableTemp(material.getMinApplicableTemp());
    materialDTO.setPotLife(material.getPotLife());
    return materialDTO;
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

}
