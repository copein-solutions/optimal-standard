package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ConstructionSystemDTO {

  private Long id;

  private String totalConsumption;

  private Integer layers;

  private String applicationMode;

  private boolean cured;

  //  @NotNull
  private Long applicationAreaId;

  private String applicationAreaName;

  private String baseConditions;

  private String supportConditions;

  private String materialAreaRestrictions;

  private List<TypeOfUseOfMaterial> materials;

}
