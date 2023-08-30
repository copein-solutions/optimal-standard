package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.ConstructionSystemComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConstructionSystemCommentRepository extends JpaRepository<ConstructionSystemComment, Long> {
    Optional<ConstructionSystemComment> findByIdAndStatusIn(Long id, List<String> status);

    List<ConstructionSystemComment> findByConstructionSystemIdAndStatusIn(Long id, List<String> status);

    void deleteByConstructionSystemId(Long id);
}
