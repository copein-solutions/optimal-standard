package com.optimal.standard.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ApplicationAreaDTO {

  private String name;

  private String specification;

  private String considerations;

  private Set<MaterialDTO> materials;

}
