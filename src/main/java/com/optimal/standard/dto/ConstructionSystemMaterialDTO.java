package com.optimal.standard.dto;

import com.optimal.standard.persistence.model.TypeOfUse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionSystemMaterialDTO {

  private Long id;

  private TypeOfUse typeOfUse;

  private MaterialDTO material;

  private Double coefficient;

  private Double performance;

  private String coefficientDescription;

  private String materialDescription;

}
