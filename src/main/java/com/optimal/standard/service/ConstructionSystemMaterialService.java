package com.optimal.standard.service;

import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import com.optimal.standard.persistence.repository.ConstructionSystemMaterialRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConstructionSystemMaterialService {

  private final ConstructionSystemMaterialRepository constructionSystemMaterialRepository;

  public void saveAllConstructionSystemMaterials(List<ConstructionSystemMaterial> constructionSystemMaterials) {
    this.constructionSystemMaterialRepository.saveAll(constructionSystemMaterials);
  }

}
