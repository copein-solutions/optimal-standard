package com.optimal.standard.persistence.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  private String name;

  private String rol;

  private String document;

  private String email;

  private String password;

  @CreationTimestamp
  private LocalDateTime dateCreated;

  @UpdateTimestamp
  private LocalDateTime dateUpdated;

}
