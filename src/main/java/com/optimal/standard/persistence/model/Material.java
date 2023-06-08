package com.optimal.standard.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

  private String name;

  private String type;

  private String classification;

  private String composition;

  private Double price;

  private Integer totalConsumption;

  private Integer handsNumber;

  private String applicationMode;

  private boolean cured;

  @JoinTable(name = "application_area_material", joinColumns = @JoinColumn(name = "material_id"), inverseJoinColumns = @JoinColumn(name =
      "application_area_id"))
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<ApplicationArea> applicationAreas;


  public void addApplicationArea(ApplicationArea applicationArea) {
    this.applicationAreas.add(applicationArea);
  }

  public void removeApplicationArea(ApplicationArea applicationArea) {
    this.applicationAreas.remove(applicationArea);
  }

}
