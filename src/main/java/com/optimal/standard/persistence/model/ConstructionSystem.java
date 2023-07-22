package com.optimal.standard.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class ConstructionSystem {

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
