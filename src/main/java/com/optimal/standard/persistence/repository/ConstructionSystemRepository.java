package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.ConstructionSystem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConstructionSystemRepository extends JpaRepository<ConstructionSystem, Long> {

    List<ConstructionSystem> findAllByDeletedFalse();

    Optional<ConstructionSystem> findByIdAndDeletedFalse(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE ConstructionSystem cs SET cs.deleted = true WHERE cs.id = :id")
    void markAsDeleted(Long id);
}
