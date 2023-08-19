package com.optimal.standard.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class MaterialFiles {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(nullable = false)
  private String name;


  @Enumerated(EnumType.STRING)
  private MaterialTypeFiles type;

  private Long size;

  @ManyToOne
  @JoinColumn(name = "material_id")
  private Material material;

  @CreationTimestamp
  private LocalDateTime dateCreated;

  @CreationTimestamp
  private LocalDateTime dateUpdated;

  public MaterialFiles(String name, Long size, MaterialTypeFiles type, Material material) {
    this.name = name;
    this.size = size;
    this.type = type;
    this.material = material;
  }

  public MaterialFiles(String name, Long size, MaterialTypeFiles type) {
    this.name = name;
    this.size = size;
    this.type = type;
  }

}
