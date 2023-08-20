package com.optimal.standard.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  private String type;

  private Long size;

  private boolean temp;

  @ManyToOne
  @JoinColumn(name = "material_id")
  private Material material;

  @CreationTimestamp
  private LocalDateTime dateCreated;

  @CreationTimestamp
  private LocalDateTime dateUpdated;

  public MaterialFiles(String name, Long size, String type, boolean temp, Material material) {
    this.name = name;
    this.type = type;
    this.size = size;
    this.temp = temp;
    this.material = material;
  }

  public MaterialFiles(String name, Long size, String type, boolean temp) {
    this.name = name;
    this.type = type;
    this.size = size;
    this.temp = temp;
  }

}
