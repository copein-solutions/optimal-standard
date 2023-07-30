package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "construction_system_material")
public class ConstructionSystemMaterial {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "material_id")
  private Material material;

  @ManyToOne
  @JoinColumn(name = "construction_system_id")
  private ConstructionSystem constructionSystem;

  @Enumerated(EnumType.STRING)
  private TypeOfUse typeOfUse;

  private Integer coefficient;

  private String coefficientDescription;

  private String materialDescription;

  public ConstructionSystemMaterial(Material material, ConstructionSystem constructionSystem, TypeOfUse typeOfUse) {
    this.material = material;
    this.constructionSystem = constructionSystem;
    this.typeOfUse = typeOfUse;
  }

}
