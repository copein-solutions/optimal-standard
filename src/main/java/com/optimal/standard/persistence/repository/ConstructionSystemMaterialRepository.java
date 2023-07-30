package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionSystemMaterialRepository extends JpaRepository<ConstructionSystemMaterial, Long> {

}
