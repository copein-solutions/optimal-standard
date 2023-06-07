package com.optimal.standard.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseMaterialDTO extends MaterialDTO {

  private List<ApplicationAreaDTO> applicationAreas;

}
