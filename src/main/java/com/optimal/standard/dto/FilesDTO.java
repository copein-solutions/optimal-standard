package com.optimal.standard.dto;

import com.optimal.standard.persistence.model.MaterialTypeFiles;
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

  private MaterialTypeFiles type;

}
