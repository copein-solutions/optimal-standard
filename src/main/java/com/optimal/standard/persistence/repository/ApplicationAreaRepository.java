package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.ApplicationArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationAreaRepository extends JpaRepository<ApplicationArea, Long> {

}
