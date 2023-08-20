package com.optimal.standard.dto;

import com.optimal.standard.persistence.model.TypeOfUse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TypeOfUseOfMaterial {

    private Long id;

    private Long materialId;

    private TypeOfUse typeOfUse;

    private Double coefficient;

    private String coefficientDescription;

    private String materialDescription;

}
