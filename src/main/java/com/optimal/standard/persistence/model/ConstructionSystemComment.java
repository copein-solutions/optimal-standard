package com.optimal.standard.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "construction_system_comment")
public class ConstructionSystemComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  private String comment;

  private LocalDate createdDate;

  @ManyToOne
  @JoinColumn(name = "construction_system_id")
  private ConstructionSystem constructionSystem;

  @ManyToOne
  @JoinColumn(name = "registered_user_id")
  private RegisteredUser registeredUser;

}
