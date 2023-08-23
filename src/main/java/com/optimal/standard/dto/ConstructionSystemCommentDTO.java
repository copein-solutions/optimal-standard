package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionSystemCommentDTO {

  private Long id;

  private String comment;

  private String userName;

}
