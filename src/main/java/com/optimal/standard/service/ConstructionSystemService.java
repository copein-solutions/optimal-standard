package com.optimal.standard.service;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.TypeOfUseOfMaterial;
import com.optimal.standard.exception.BadRequestException;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.repository.ConstructionSystemMaterialRepository;
import com.optimal.standard.persistence.repository.ConstructionSystemRepository;
import com.optimal.standard.util.ConstructionSystemMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.optimal.standard.service.ApplicationAreaService.APPLICATION_AREA_NOT_FOUND_MESSAGE;

@Service
@AllArgsConstructor
public class ConstructionSystemService {

  private final ApplicationAreaService applicationAreaService;

  private final MaterialService materialService;

  private final ConstructionSystemRepository constructionSystemRepository;

  private final ConstructionSystemMaterialRepository constructionSystemMaterialRepository;

  public void saveConstructionSystem(ConstructionSystemDTO request) {
    Long applicationAreaId = request.getApplicationAreaId();
    this.validateApplicationArea(applicationAreaId);
    this.validateTypeOfUseOfMaterials(request.getMaterials());

    ConstructionSystem constructionSystem = this.createConstructionSystem(applicationAreaId, request);

    List<ConstructionSystemMaterial> constructionSystemMaterials = request
            .getMaterials()
            .stream()
            .map(typeOfUseOfMaterial -> this.buildConstructionSystemMaterial(typeOfUseOfMaterial, constructionSystem))
            .collect(Collectors.toList());

    this.constructionSystemMaterialRepository.saveAll(constructionSystemMaterials);
  }

  private ConstructionSystem createConstructionSystem(Long applicationAreaId, ConstructionSystemDTO request) {
    ApplicationArea applicationArea = this.applicationAreaService.findApplicationAreaById(applicationAreaId);
    return this.constructionSystemRepository.save(ConstructionSystem
            .builder()
            .id(request.getId())
            .totalConsumption(request.getTotalConsumption())
            .layers(request.getLayers())
            .applicationMode(request.getApplicationMode())
            .cured(request.isCured())
            .applicationArea(applicationArea)
            .build());
  }

  private ConstructionSystemMaterial buildConstructionSystemMaterial(TypeOfUseOfMaterial typeOfUseOfMaterial,
                                                                     ConstructionSystem constructionSystem) {
    Material material = this.materialService.findMaterialById(typeOfUseOfMaterial.getId());
    return new ConstructionSystemMaterial(material, constructionSystem, typeOfUseOfMaterial.getTypeOfUse());
  }

  private void validateApplicationArea(Long applicationAreaId) {
    if (!this.applicationAreaService.existById(applicationAreaId)) {
      throw new EntityNotFoundException(APPLICATION_AREA_NOT_FOUND_MESSAGE + applicationAreaId);
    }
  }

  private void validateTypeOfUseOfMaterials(List<TypeOfUseOfMaterial> typeOfUseOfMaterials) {
    List<Long> materialIds = typeOfUseOfMaterials
            .stream()
            .map(TypeOfUseOfMaterial::getId)
            .toList();

    if (!new HashSet<>(this.materialService
            .findAllByIds(materialIds)
            .stream()
            .map(MaterialDTO::getId)
            .toList()).containsAll(materialIds)) {
      throw new BadRequestException("Some of the materials provided do not exist");
    }
  }

  public List<ConstructionSystemDTO> findAll() {
    return this.constructionSystemRepository
            .findAll()
            .stream()
            .map(ConstructionSystemMapperUtils::toDTO)
            .toList();
  }
}