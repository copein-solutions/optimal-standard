package com.optimal.standard.dto;

import com.optimal.standard.persistence.model.TypeOfUse;
import lombok.Data;

@Data
public class TypeOfUseOfMaterial {

  private Long id;

  private TypeOfUse typeOfUse;

  private Integer coefficient;

  private String coefficientDescription;

  private String materialDescription;

}
