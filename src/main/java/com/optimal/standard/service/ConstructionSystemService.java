package com.optimal.standard.service;

import static com.optimal.standard.persistence.model.SystemCategory.ALTERNATIVE_OPTIMAL_STANDARD;
import static com.optimal.standard.persistence.model.SystemCategory.OPTIMAL_STANDARD;
import static com.optimal.standard.persistence.model.SystemCategory.REMOVE;
import static com.optimal.standard.service.ApplicationAreaService.APPLICATION_AREA_NOT_FOUND_MESSAGE;
import static com.optimal.standard.util.ConstructionSystemMapperUtils.toConstructionSystem;
import static com.optimal.standard.util.ConstructionSystemMapperUtils.toResponseDTO;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.ConstructionSystemMaterialDTO;
import com.optimal.standard.dto.ConstructionSystemMaterialPrices;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.dto.SystemCategoryDTO;
import com.optimal.standard.dto.TypeOfUseOfMaterial;
import com.optimal.standard.exception.BadRequestException;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.model.TypeOfUse;
import com.optimal.standard.persistence.repository.ConstructionSystemCommentRepository;
import com.optimal.standard.persistence.repository.ConstructionSystemRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
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

  private final ConstructionSystemCommentRepository constructionSystemCommentRepository;

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

    // TODO: Contemplar solo los deleted false;
    if (!new HashSet<>(this.materialService
        .findAllByIds(materialIds)
        .stream()
        .map(MaterialDTO::getId)
        .toList()).containsAll(materialIds)) {
      throw new BadRequestException("Some of the materials provided do not exist");
    }
  }

  public List<ResponseConstructionSystemDTO> findAll() {
    return this.constructionSystemRepository
        .findAllByDeletedFalse()
        .stream()
        .map(this::buildResponseConstructionSystemDTO)
        .toList();
  }

  public ResponseConstructionSystemDTO findById(Long id) {
    return this.constructionSystemRepository
        .findById(id)
        .map(this::buildResponseConstructionSystemDTO)
        .orElseThrow(() -> new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id));
  }

  private ResponseConstructionSystemDTO buildResponseConstructionSystemDTO(ConstructionSystem constructionSystem) {
    double totalPrice = this.getSystemTotalPrice(constructionSystem);
    List<ConstructionSystemMaterialDTO> csm =
        this.constructionSystemMaterialService.toConstructionSystemMaterialsDTO(constructionSystem.getConstructionSystemMaterials());
    return toResponseDTO(constructionSystem, totalPrice, csm);
  }

  public void updateConstructionSystem(Long id, ConstructionSystemDTO request) {
    this.validateTypeOfUseOfMaterials(request.getMaterials());
    Long applicationAreaId = request.getApplicationAreaId();
    this.validateApplicationArea(applicationAreaId);

    this.constructionSystemRepository
        .findByIdAndDeletedFalse(id)
        .ifPresentOrElse(constructionSystemDatabase -> {

          ApplicationArea applicationArea = this.applicationAreaService.findApplicationAreaById(applicationAreaId);
          ConstructionSystem constructionSystem = toConstructionSystem(request, applicationArea);
          constructionSystem.setId(constructionSystemDatabase.getId());
          constructionSystem.setSystemCategory(constructionSystemDatabase.getSystemCategory());
          constructionSystem.setConstructionSystemComments(constructionSystemDatabase.getConstructionSystemComments());
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

  private double getSystemTotalPrice(ConstructionSystem constructionSystem) {

    AtomicReference<Double> totalPluginPrice = new AtomicReference<>(0.0);
    double totalConsumption = constructionSystem.getTotalConsumption();
    Map<TypeOfUse, ConstructionSystemMaterialPrices> materialPrices = constructionSystem
        .getConstructionSystemMaterials()
        .stream()
        .filter(csm -> !TypeOfUse.PLUGIN_MATERIAL.equals(csm.getTypeOfUse()))
        .collect(Collectors.toMap(ConstructionSystemMaterial::getTypeOfUse, this::buildConstructionSystemMaterialPrices));

    List<ConstructionSystemMaterialPrices> pluginPrices = constructionSystem
        .getConstructionSystemMaterials()
        .stream()
        .filter(csm -> TypeOfUse.PLUGIN_MATERIAL.equals(csm.getTypeOfUse()))
        .map(this::buildConstructionSystemMaterialPrices)
        .peek(csmp -> totalPluginPrice.accumulateAndGet((csmp.getUnitPrice() * csmp.getCoefficient()), Double::sum))
        .toList();

    double materialBasePrice = materialPrices
        .get(TypeOfUse.BASE)
        .getUnitPrice();
    double totalMeshPrice = materialPrices
        .getOrDefault(TypeOfUse.TOTAL_MESH, new ConstructionSystemMaterialPrices(0.0, 0.0))
        .getUnitPrice();
    ConstructionSystemMaterialPrices partialMeshPrices =
        materialPrices.getOrDefault(TypeOfUse.PARTIAL_MESH, new ConstructionSystemMaterialPrices(0.0, 0.0));
    double partialMeshPrice = partialMeshPrices.getUnitPrice();
    double coefficientPartialMesh = partialMeshPrices.getCoefficient();
    return (materialBasePrice * totalConsumption) + totalMeshPrice + (partialMeshPrice * coefficientPartialMesh) + totalPluginPrice.get();
  }

  private ConstructionSystemMaterialPrices buildConstructionSystemMaterialPrices(ConstructionSystemMaterial csm) {
    return new ConstructionSystemMaterialPrices(this.globalVariableService.getUnitPrice(csm.getMaterial()), csm.getCoefficient());
  }

  public void deleteConstructionSystem(Long id) {
    this.constructionSystemRepository
        .findByIdAndDeletedFalse(id)
        .orElseThrow(() -> new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id));

    this.constructionSystemCommentRepository.deleteByConstructionSystemId(id);

    this.constructionSystemRepository.markAsDeleted(id);
  }

  public void updateSystemCategory(Long id, SystemCategoryDTO request) {
    ConstructionSystem constructionSystem = this.constructionSystemRepository
        .findByIdAndDeletedFalse(id)
        .map(cs -> {
          if (List
              .of(OPTIMAL_STANDARD, ALTERNATIVE_OPTIMAL_STANDARD, REMOVE)
              .contains(request.getType())) {
            cs.setSystemCategory(request
                .getType()
                .name());
          }
          return cs;
        })
        .orElseThrow(() -> new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id));
    this.constructionSystemRepository.save(constructionSystem);
  }

}
