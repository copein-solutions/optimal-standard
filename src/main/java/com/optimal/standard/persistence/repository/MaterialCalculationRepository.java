package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.MaterialCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialCalculationRepository extends JpaRepository<MaterialCalculation, Long> {

}
