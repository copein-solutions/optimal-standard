package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialDTO {

    private String name;

    private String brand;

    private Double price;

    private String type;

    private String packaging;

    private Integer quantity;

}
