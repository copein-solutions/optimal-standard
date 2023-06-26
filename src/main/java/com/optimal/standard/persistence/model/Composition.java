package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String classification;

    private String totalConsumption;

    private Integer layers;

    private Integer applicationMode;

    private boolean cured;

    @ManyToOne
    @JoinColumn(name = "application_area_id")
    private ApplicationArea applicationArea;

}
