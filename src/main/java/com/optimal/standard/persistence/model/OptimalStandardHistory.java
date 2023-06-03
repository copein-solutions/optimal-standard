package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class OptimalStandardHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Date date;

    private String type;

    @ManyToOne
    @JoinColumn(name = "application_area_id")
    private ApplicationArea applicationArea;

    private String historyJson;
}
