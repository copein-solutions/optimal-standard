package com.optimal.standard.service;

import static com.optimal.standard.util.MaterialMapperUtils.toMaterialDTO;

import com.optimal.standard.dto.ConstructionSystemMaterialDTO;
import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import com.optimal.standard.persistence.repository.ConstructionSystemMaterialRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConstructionSystemMaterialService {

  private final ConstructionSystemMaterialRepository constructionSystemMaterialRepository;

  private final GlobalVariableService globalVariableService;

  public void saveAllConstructionSystemMaterials(List<ConstructionSystemMaterial> constructionSystemMaterials) {
    this.constructionSystemMaterialRepository.saveAll(constructionSystemMaterials);
  }

  public List<ConstructionSystemMaterialDTO> toConstructionSystemMaterialsDTO(
      List<ConstructionSystemMaterial> constructionSystemMaterials) {
    return constructionSystemMaterials
        .stream()
        .map(this::toConstructionSystemMaterialsDTO)
        .collect(Collectors.toList());
  }

  private ConstructionSystemMaterialDTO toConstructionSystemMaterialsDTO(ConstructionSystemMaterial constructionSystemMaterial) {
    return ConstructionSystemMaterialDTO
        .builder()
        .id(constructionSystemMaterial.getId())
        .typeOfUse(constructionSystemMaterial.getTypeOfUse())
        .material(toMaterialDTO(constructionSystemMaterial.getMaterial(),
            this.globalVariableService.getUnitPrice(constructionSystemMaterial.getMaterial())))
        .coefficient(constructionSystemMaterial.getCoefficient())
        .performance(constructionSystemMaterial.getPerformance())
        .coefficientDescription(constructionSystemMaterial.getCoefficientDescription())
        .materialDescription(constructionSystemMaterial.getMaterialDescription())
        .build();
  }

}
