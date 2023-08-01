package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionSystemMaterialRepository extends JpaRepository<ConstructionSystemMaterial, Long> {

  @Transactional
  @Modifying
  @Query("DELETE FROM ConstructionSystemMaterial csm WHERE csm.id IN :ids")
  void deleteAllByIds(List<Long> ids);

}
