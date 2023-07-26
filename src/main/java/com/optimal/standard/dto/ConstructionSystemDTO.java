package com.optimal.standard.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstructionSystemDTO {

  private Long Id;

  private String totalConsumption;

  private Integer layers;

  private String applicationMode;

  private boolean cured;

  //  @NotNull
  private Long applicationAreaId;

  private List<TypeOfUseOfMaterial> materials;

}
