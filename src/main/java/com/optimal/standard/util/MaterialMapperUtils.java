package com.optimal.standard.util;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.optimal.standard.dto.FilesDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.model.MaterialFiles;

public interface MaterialMapperUtils {

  static Double getUnitPrice(Material material) {
    return material.getPresentationPrice() / material.getPresentationQuantity();
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
    materialDTO.setUnitPrice(getUnitPrice(material));
    materialDTO.setFiles(emptyIfNull(material.getMaterialFiles())
        .stream()
        .map(MaterialMapperUtils::toDTO)
        .toList());
    return materialDTO;
  }

  static FilesDTO toDTO(MaterialFiles materialFiles) {
    return FilesDTO
        .builder()
        .id(materialFiles.getId())
        .name(materialFiles.getName())
        .type(materialFiles.getType())
        .size(materialFiles.getSize())
        .build();
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
