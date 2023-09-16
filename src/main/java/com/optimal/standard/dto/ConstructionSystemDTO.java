package com.optimal.standard.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConstructionSystemDTO {

  private Long id;

  private double totalConsumption;

  private Integer layers;

  private String applicationMode;

  private boolean cured;

  private double totalPrice;

  //  @NotNull
  private Long applicationAreaId;

  private String applicationAreaName;

  private String baseConditions;

  private String supportConditions;

  private String materialAreaRestrictions;

  private String materialAreaDescription;

  private List<TypeOfUseOfMaterial> materials;

}
