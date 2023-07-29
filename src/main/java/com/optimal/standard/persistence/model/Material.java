package com.optimal.standard.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.Set;
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
public class Material {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(nullable = false)
  private String product;

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

  private String potLife;

  private String minApplicableTemp;

  @JoinTable(name = "construction_system_material", joinColumns = @JoinColumn(name = "material_id"), inverseJoinColumns =
  @JoinColumn(name = "construction_system_id"))
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<ConstructionSystem> constructionSystems;


}
