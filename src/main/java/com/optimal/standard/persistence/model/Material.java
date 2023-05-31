package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;

    private String type;

    private String classification;

    private String composition;

    private Double price;

    private Integer totalConsumption;

    private Integer handsNumber;

    private String applicationMode;

    private String cured;

    @ManyToMany(mappedBy = "materials")
    private Set<ApplicationArea> applicationAreas;
}
