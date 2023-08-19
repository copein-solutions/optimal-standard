package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.MaterialFiles;
import com.optimal.standard.persistence.model.MaterialTypeFiles;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialFileRepository extends JpaRepository<MaterialFiles, Long> {

  List<MaterialFiles> findAllByMaterialIdAndTypeAndNameIn(Long materialId, MaterialTypeFiles type, List<String> fileName);

}
