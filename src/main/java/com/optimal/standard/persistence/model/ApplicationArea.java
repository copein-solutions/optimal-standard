package com.optimal.standard.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ApplicationArea {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  private String name;

  private String specification;

  private String considerations;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "applicationAreas")
  private Set<Material> materials;

}
