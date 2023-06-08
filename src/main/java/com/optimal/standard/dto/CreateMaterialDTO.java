package com.optimal.standard.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMaterialDTO extends MaterialDTO {

  private List<Long> applicationAreaIds;

}
