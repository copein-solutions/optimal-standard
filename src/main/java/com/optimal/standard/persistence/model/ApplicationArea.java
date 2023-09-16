package com.optimal.standard.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

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

  @Column(columnDefinition = "text")
  private String specification;

  @Column(columnDefinition = "text")
  private String considerations;

  private boolean deleted;

  @OneToMany(mappedBy = "applicationArea", fetch = FetchType.EAGER)
  @Where(clause = "deleted = false")
  private List<ConstructionSystem> constructionSystems = new ArrayList<>();

}
