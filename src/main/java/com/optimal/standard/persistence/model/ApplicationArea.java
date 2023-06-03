package com.optimal.standard.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @JoinTable(name = "application_area_material", joinColumns = @JoinColumn(name = "application_area_id"), inverseJoinColumns =
  @JoinColumn(name = "material_id"))
  public Set<Material> materials;

}
