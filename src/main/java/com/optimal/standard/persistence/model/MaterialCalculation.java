package com.optimal.standard.persistence.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class MaterialCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;

    private String formula;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
}
