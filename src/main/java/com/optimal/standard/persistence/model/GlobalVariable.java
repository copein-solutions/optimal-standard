package com.optimal.standard.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class GlobalVariable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  private String name;

  private double value;
  
}
