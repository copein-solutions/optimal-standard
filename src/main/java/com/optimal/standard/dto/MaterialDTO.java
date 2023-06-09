package com.optimal.standard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MaterialDTO {

  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String brand;

  @NotNull
  private Integer presentationQuantity;

  @NotBlank
  private String presentationUnit;

  @NotNull
  private Double presentationPrice;

  @NotNull
  private LocalDate priceDate;

  @NotBlank
  private String currency;

  @NotBlank
  private String type;

  @NotBlank
  private String component;

}
