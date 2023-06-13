package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;

    private String brand;

    private Double price;

    private String type;

    private String packaging;

    private Integer quantity;

    @JoinTable(name = "composition_material", joinColumns = @JoinColumn(name = "material_id"), inverseJoinColumns = @JoinColumn(name =
            "composition_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Composition> compositions;


}
