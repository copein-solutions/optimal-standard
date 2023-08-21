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
public class ConstructionSystem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  private String totalConsumption;

  private Integer layers;

  private String applicationMode;

  private boolean cured;

  private double totalPrice;

  private String baseConditions;

  private String supportConditions;

  private String materialAreaRestrictions;

  private String materialAreaDescription;

  private boolean deleted;

  @ManyToOne
  @JoinColumn(name = "application_area_id")
  private ApplicationArea applicationArea;

  @OneToMany(mappedBy = "constructionSystem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<ConstructionSystemMaterial> constructionSystemMaterials = new ArrayList<>();

  @OneToMany(mappedBy = "constructionSystem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<ConstructionSystemComment> constructionSystemComments = new ArrayList<>();

}
