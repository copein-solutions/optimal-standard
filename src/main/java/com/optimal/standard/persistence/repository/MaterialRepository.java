package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.Material;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

  List<Material> findAllByType(String type);

}
