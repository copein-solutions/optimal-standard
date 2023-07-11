package com.optimal.standard.util;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.dto.CompositionDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.model.Composition;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.model.ApplicationArea;
import java.util.stream.Collectors;

public interface MapperUtils {

  static ResponseMaterialDTO toResponseDTO(Material material) {
    ResponseMaterialDTO responseMaterialDTO = new ResponseMaterialDTO();
    responseMaterialDTO.setId(material.getId());
    responseMaterialDTO.setName(material.getName());
    responseMaterialDTO.setBrand(material.getBrand());
    responseMaterialDTO.setPresentationQuantity(material.getPresentationQuantity());
    responseMaterialDTO.setPresentationUnit(material.getPresentationUnit());
    responseMaterialDTO.setPresentationPrice(material.getPresentationPrice());
    responseMaterialDTO.setPriceDate(material.getPriceDate());
    responseMaterialDTO.setCurrency(material.getCurrency());
    responseMaterialDTO.setType(material.getType());
    responseMaterialDTO.setComponent(material.getComponent());
    responseMaterialDTO.setCompositions(emptyIfNull(material.getCompositions())
        .stream()
        .map(MapperUtils::toResponseDTO)
        .collect(Collectors.toList()));
    return responseMaterialDTO;
  }

  static ApplicationArea toApplicationAreaMapper(ApplicationAreaDTO applicationArea) {
    return ApplicationArea
        .builder()
        .name(applicationArea.getName())
        .specification(applicationArea.getSpecification())
        .considerations(applicationArea.getConsiderations())
        .build();
  }

  static Material toMaterialMapper(MaterialDTO material) {
    return Material
            .builder()
            .name(material.getName())
            .brand(material.getBrand())
            .presentationQuantity(material.getPresentationQuantity())
            .presentationUnit(material.getPresentationUnit())
            .presentationPrice(material.getPresentationPrice())
            .priceDate(material.getPriceDate())
            .currency(material.getCurrency())
            .type(material.getType())
            .component(material.getComponent())
            .build();
  }

  static CompositionDTO toResponseDTO(Composition composition) {
    return CompositionDTO
        .builder()
        .classification(composition.getClassification())
        .totalConsumption(composition.getTotalConsumption())
        .layers(composition.getLayers())
        .applicationMode(composition.getApplicationMode())
        .cured(composition.isCured())
        .build();
  }

}
