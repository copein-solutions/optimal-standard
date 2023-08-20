package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.MaterialFiles;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MaterialFileRepository extends JpaRepository<MaterialFiles, Long> {

  List<MaterialFiles> findAllByTempTrueAndMaterialIdAndIdIn(Long materialId, List<Long> ids);

  List<MaterialFiles> findAllByMaterialId(Long materialId);

  List<MaterialFiles> findAllByTempTrueAndIdIn(List<Long> ids);

  @Transactional
  void deleteAllByMaterialId(Long materialId);

  @Transactional
  void deleteAllByIdIn(List<Long> ids);

}
