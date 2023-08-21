package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FilesDTO {

  private Long id;

  private String name;

  private Long size;

  private String type;

}
