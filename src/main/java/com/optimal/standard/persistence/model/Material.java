package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Integer presentationQuantity;

    @Column(nullable = false)
    private String presentationUnit;

    @Column(nullable = false)
    private Double presentationPrice;

    @Column(nullable = false)
    private LocalDate priceDate;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String component;

    @JoinTable(name = "composition_material", joinColumns = @JoinColumn(name = "material_id"), inverseJoinColumns = @JoinColumn(name =
            "composition_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Composition> compositions;


}
