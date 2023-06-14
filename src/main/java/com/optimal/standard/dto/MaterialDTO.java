package com.optimal.standard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDTO {

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
