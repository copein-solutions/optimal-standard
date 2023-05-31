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
public class ApplicationArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;

    private String specification;

    private String considerations;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "application_area_material",
            joinColumns = @JoinColumn(name = "application_area_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    public Set<Material> materials;
}
