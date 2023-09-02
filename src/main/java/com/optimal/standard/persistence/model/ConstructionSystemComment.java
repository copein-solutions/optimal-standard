package com.optimal.standard.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "construction_system_comment")
public class ConstructionSystemComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(columnDefinition = "text")
  private String comment;

  private LocalDate createdDate;

  @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default 'PENDING'")
  private String status;

  @ManyToOne
  @JoinColumn(name = "construction_system_id")
  private ConstructionSystem constructionSystem;

  @ManyToOne
  @JoinColumn(name = "registered_user_id")
  private RegisteredUser registeredUser;

}
