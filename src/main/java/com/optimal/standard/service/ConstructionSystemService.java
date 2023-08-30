package com.optimal.standard.service;

import static com.optimal.standard.service.ApplicationAreaService.APPLICATION_AREA_NOT_FOUND_MESSAGE;
import static com.optimal.standard.util.ConstructionSystemMapperUtils.toConstructionSystem;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.dto.TypeOfUseOfMaterial;
import com.optimal.standard.exception.BadRequestException;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.repository.ConstructionSystemRepository;
import com.optimal.standard.util.ConstructionSystemMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConstructionSystemService {

  public static final String CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE = "Construction system not found with ID: ";

  private final ApplicationAreaService applicationAreaService;

  private final MaterialService materialService;

  private final ConstructionSystemMaterialService constructionSystemMaterialService;

  private final ConstructionSystemRepository constructionSystemRepository;

  private final GlobalVariableService globalVariableService;

  public void saveConstructionSystem(ConstructionSystemDTO request) {
    Long applicationAreaId = request.getApplicationAreaId();
    this.validateApplicationArea(applicationAreaId);
    this.validateTypeOfUseOfMaterials(request.getMaterials());

    ConstructionSystem constructionSystem = this.createConstructionSystem(applicationAreaId, request);
    List<ConstructionSystemMaterial> constructionSystemMaterials = this.buildConstructionSystemMaterials(request, constructionSystem);
    this.constructionSystemMaterialService.saveAllConstructionSystemMaterials(constructionSystemMaterials);
  }

  private ConstructionSystem createConstructionSystem(Long applicationAreaId, ConstructionSystemDTO request) {
    ApplicationArea applicationArea = this.applicationAreaService.findApplicationAreaById(applicationAreaId);
    return this.constructionSystemRepository.save(toConstructionSystem(request, applicationArea));
  }

  private ConstructionSystemMaterial buildConstructionSystemMaterial(TypeOfUseOfMaterial typeOfUseOfMaterial,
      ConstructionSystem constructionSystem) {
    Material material = this.materialService.findMaterialById(typeOfUseOfMaterial.getMaterialId());
    return new ConstructionSystemMaterial(typeOfUseOfMaterial.getId(), material, constructionSystem, typeOfUseOfMaterial.getTypeOfUse(),
        typeOfUseOfMaterial.getCoefficient(), typeOfUseOfMaterial.getCoefficientDescription(),
        typeOfUseOfMaterial.getMaterialDescription());
  }

  private void validateApplicationArea(Long applicationAreaId) {
    if (!this.applicationAreaService.existById(applicationAreaId)) {
      throw new EntityNotFoundException(APPLICATION_AREA_NOT_FOUND_MESSAGE + applicationAreaId);
    }
  }

  private void validateTypeOfUseOfMaterials(List<TypeOfUseOfMaterial> typeOfUseOfMaterials) {
    List<Long> materialIds = typeOfUseOfMaterials
        .stream()
        .map(TypeOfUseOfMaterial::getMaterialId)
        .toList();

    if (!new HashSet<>(this.materialService
        .findAllByIds(materialIds)
        .stream()
        .map(MaterialDTO::getId)
        .toList()).containsAll(materialIds)) {
      throw new BadRequestException("Some of the materials provided do not exist");
    }
  }

  public List<ResponseConstructionSystemDTO> findAll() {
    double quotationDollar = this.globalVariableService.getQuotationDollar();
    return this.constructionSystemRepository
        .findAll()
        .stream()
        .map(cs -> ConstructionSystemMapperUtils.toResponseDTO(cs, quotationDollar))
        .toList();
  }

  public ResponseConstructionSystemDTO findById(Long id) {
    double quotationDollar = this.globalVariableService.getQuotationDollar();
    return this.constructionSystemRepository
        .findById(id)
        .map(cs -> ConstructionSystemMapperUtils.toResponseDTO(cs, quotationDollar))
        .orElseThrow(() -> new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id));
  }

  public void updateConstructionSystem(Long id, ConstructionSystemDTO request) {
    this.validateTypeOfUseOfMaterials(request.getMaterials());
    Long applicationAreaId = request.getApplicationAreaId();
    this.validateApplicationArea(applicationAreaId);

    this.constructionSystemRepository
        .findById(id)
        .ifPresentOrElse(constructionSystemDatabase -> {

          ApplicationArea applicationArea = this.applicationAreaService.findApplicationAreaById(applicationAreaId);
          ConstructionSystem constructionSystem = toConstructionSystem(request, applicationArea);
          constructionSystem.setId(constructionSystemDatabase.getId());

          List<ConstructionSystemMaterial> constructionSystemMaterials = this.buildConstructionSystemMaterials(request, constructionSystem);
          constructionSystem.setConstructionSystemMaterials(constructionSystemMaterials);
          this.constructionSystemRepository.save(constructionSystem);

        }, () -> {
          throw new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id);
        });
  }

  private List<ConstructionSystemMaterial> buildConstructionSystemMaterials(ConstructionSystemDTO request,
      ConstructionSystem constructionSystem) {
    return request
        .getMaterials()
        .stream()
        .map(typeOfUseOfMaterial -> this.buildConstructionSystemMaterial(typeOfUseOfMaterial, constructionSystem))
        .collect(Collectors.toList());
  }

}
