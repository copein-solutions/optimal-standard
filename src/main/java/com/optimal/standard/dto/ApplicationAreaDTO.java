package com.optimal.standard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ApplicationAreaDTO {
    
    private Long Id;

    @NotBlank
    private String name;

    private String considerations;

}
