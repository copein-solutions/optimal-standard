package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.MaterialFiles;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MaterialFileRepository extends JpaRepository<MaterialFiles, Long> {

  List<MaterialFiles> findAllByMaterialIdAndTempTrueAndNameIn(Long materialId, List<String> fileName);

  @Transactional
  void deleteAllByMaterialId(Long materialId);

}