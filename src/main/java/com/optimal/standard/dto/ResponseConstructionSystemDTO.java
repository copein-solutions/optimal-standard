package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseConstructionSystemDTO {

    private Long id;

    private String totalConsumption;

    private Integer layers;

    private String applicationMode;

    private boolean cured;

    private String baseConditions;

    private String supportConditions;

    private String materialAreaRestrictions;

    private String materialAreaDescription;

    private ApplicationAreaDTO applicationArea;

    private List<ConstructionSystemMaterialDTO> materials;

}
