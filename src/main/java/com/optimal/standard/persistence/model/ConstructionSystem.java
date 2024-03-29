package com.optimal.standard.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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

  private double totalConsumption;

  private Integer layers;

  private String applicationMode;

  private boolean cured;

  @Column(columnDefinition = "text")
  private String baseConditions;

  @Column(columnDefinition = "text")
  private String supportConditions;

  @Column(columnDefinition = "text")
  private String materialAreaRestrictions;

  @Column(columnDefinition = "text")
  private String materialAreaDescription;

  private String systemCategory;

  private boolean deleted;

  @ManyToOne
  @JoinColumn(name = "application_area_id")
  private ApplicationArea applicationArea;

  @OneToMany(mappedBy = "constructionSystem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<ConstructionSystemMaterial> constructionSystemMaterials = new ArrayList<>();

  @OneToMany(mappedBy = "constructionSystem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<ConstructionSystemComment> constructionSystemComments = new ArrayList<>();

}
