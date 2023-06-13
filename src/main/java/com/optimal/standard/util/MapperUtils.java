package com.optimal.standard.util;

import com.optimal.standard.dto.CompositionDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.model.Composition;
import com.optimal.standard.persistence.model.Material;

import java.util.stream.Collectors;

public interface MapperUtils {

    static ResponseMaterialDTO toResponseDTO(Material material) {
        ResponseMaterialDTO responseMaterialDTO = new ResponseMaterialDTO();
        responseMaterialDTO.setName(material.getName());
        responseMaterialDTO.setType(material.getType());
        responseMaterialDTO.setCompositions(material
                .getCompositions()
                .stream()
                .map(MapperUtils::toResponseDTO)
                .collect(Collectors.toList()));
        return responseMaterialDTO;
    }

    static Material toMaterialMapper(MaterialDTO material) {
        return Material
                .builder()
                .name(material.getName())
                .brand(material.getBrand())
                .price(material.getPrice())
                .type(material.getType())
                .packaging(material.getPackaging())
                .quantity(material.getQuantity())
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
