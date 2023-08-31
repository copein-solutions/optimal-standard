package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.ConstructionSystemComment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ConstructionSystemCommentRepository extends JpaRepository<ConstructionSystemComment, Long> {
    List<ConstructionSystemComment> findByConstructionSystemIdAndStatusIn(Long id, List<String> status);

    @Transactional
    @Modifying
    void deleteByConstructionSystemId(Long id);
}
