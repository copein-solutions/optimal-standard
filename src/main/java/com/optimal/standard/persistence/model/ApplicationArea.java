package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "applicationArea")
    private List<Composition> compositions = new ArrayList<>();

}
