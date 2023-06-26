package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompositionDTO {

    private String classification;

    private String totalConsumption;

    private Integer layers;

    private Integer applicationMode;

    private boolean cured;
}
