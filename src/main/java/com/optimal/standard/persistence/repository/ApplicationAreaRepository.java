package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.ApplicationArea;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationAreaRepository extends JpaRepository<ApplicationArea, Long> {

  List<ApplicationArea> findAllByDeletedFalse();

  Optional<ApplicationArea> findByIdAndDeletedFalse(Long id);


  boolean existsByIdAndDeletedFalse(Long id);

  @Transactional
  @Modifying
  @Query("UPDATE ApplicationArea a SET a.deleted = true WHERE a.id = :id")
  void markAsDeleted(Long id);

}
