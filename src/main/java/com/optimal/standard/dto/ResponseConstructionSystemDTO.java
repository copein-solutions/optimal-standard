package com.optimal.standard.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseConstructionSystemDTO {

  private Long id;

  private double totalConsumption;

  private Integer layers;

  private String applicationMode;

  private boolean cured;

  private double totalPrice;

  private String baseConditions;

  private String supportConditions;

  private String materialAreaRestrictions;

  private String materialAreaDescription;

  private ApplicationAreaDTO applicationArea;

  private List<ConstructionSystemMaterialDTO> materials;

}
