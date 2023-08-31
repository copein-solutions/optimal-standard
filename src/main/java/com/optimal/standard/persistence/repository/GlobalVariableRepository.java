package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.GlobalVariable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalVariableRepository extends JpaRepository<GlobalVariable, Long> {

  Optional<GlobalVariable> findByName(String laborCost);

}
