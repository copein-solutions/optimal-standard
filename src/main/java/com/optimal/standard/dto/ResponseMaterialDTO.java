package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseMaterialDTO extends MaterialDTO {

    private List<CompositionDTO> compositions;

}
