package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.Material;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

  List<Material> findAllByTypeAndDeletedFalse(String type);

  List<Material> findAllByDeletedFalse();

  List<Material> findMaterialsByIdInAndDeletedFalse(List<Long> ids);

  Optional<Material> findByIdAndDeletedFalse(Long id);

  @Transactional
  @Modifying
  @Query("UPDATE Material m SET m.deleted = true WHERE m.id = :id")
  void markAsDeleted(Long id);

}
