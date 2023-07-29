package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.*;

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

  private String totalConsumption;

  private Integer layers;

  private String applicationMode;

  private boolean cured;

  private String baseConditions;

  private String supportConditions;

  private String materialAreaRestrictions;

  @ManyToOne
  @JoinColumn(name = "application_area_id")
  private ApplicationArea applicationArea;

}
