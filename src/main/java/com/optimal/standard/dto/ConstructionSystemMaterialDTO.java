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

  private TypeOfUse typeOfUse;

  private MaterialDTO material;

  private Integer coefficient;

  private String coefficientDescription;

  private String materialDescription;

}