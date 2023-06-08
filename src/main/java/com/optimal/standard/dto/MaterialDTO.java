package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
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

}
