package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.OptimalStandardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptimalStandardHistoryRepository extends JpaRepository<OptimalStandardHistory, Long> {

}
