package com.optimal.standard.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class MaterialDTO {

  private String name;

  private String type;

  private String classification;

  private String composition;

  private Double price;

  private Integer totalConsumption;

  private Integer handsNumber;

  private String applicationMode;

  private boolean cured;

  private List<ApplicationAreaDTO> applicationAreas;

}
