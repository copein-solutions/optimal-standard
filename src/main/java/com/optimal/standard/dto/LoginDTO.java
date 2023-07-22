package com.optimal.standard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginDTO {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

}
