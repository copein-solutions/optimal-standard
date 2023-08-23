package com.optimal.standard.service;

import com.optimal.standard.dto.*;
import com.optimal.standard.exception.BadRequestException;
import com.optimal.standard.persistence.model.*;
import com.optimal.standard.persistence.repository.ConstructionSystemCommentRepository;
import com.optimal.standard.persistence.repository.ConstructionSystemRepository;
import com.optimal.standard.persistence.repository.UserRepository;
import com.optimal.standard.util.ConstructionSystemMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.optimal.standard.service.ApplicationAreaService.APPLICATION_AREA_NOT_FOUND_MESSAGE;
import static com.optimal.standard.util.ConstructionSystemMapperUtils.toConstructionSystem;

@Service
@AllArgsConstructor
public class ConstructionSystemService {

  public static final String CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE = "Construction system not found with ID: ";

  private final ApplicationAreaService applicationAreaService;

  private final MaterialService materialService;

  private final ConstructionSystemMaterialService constructionSystemMaterialService;

  private final ConstructionSystemRepository constructionSystemRepository;

  private final ConstructionSystemCommentRepository constructionSystemCommentRepository;

  private final UserRepository userRepository;

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
    return new ConstructionSystemMaterial(
            typeOfUseOfMaterial.getId(),
            material,
            constructionSystem,
            typeOfUseOfMaterial.getTypeOfUse(),
            typeOfUseOfMaterial.getCoefficient(),
            typeOfUseOfMaterial.getCoefficientDescription(),
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
    return this.constructionSystemRepository
            .findAll()
            .stream()
            .map(ConstructionSystemMapperUtils::toResponseDTO)
            .toList();
  }

  public ResponseConstructionSystemDTO findById(Long id) {
    return this.constructionSystemRepository
            .findById(id)
            .map(ConstructionSystemMapperUtils::toResponseDTO)
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

  public void saveConstructionSystemComment(Long id, ConstructionSystemCommentDTO request) {
    this.constructionSystemRepository
            .findById(id)
            .ifPresentOrElse(constructionSystemDatabase -> {
              this.constructionSystemCommentRepository.save(new ConstructionSystemComment(
                      null,
                      request.getComment(),
                      LocalDate.now(),
                      constructionSystemDatabase,
                      this.userRepository.findByUsername(request.getUserName())
              ));
            }, () -> {
              throw new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id);
            });
  }

  public List<ResponseConstructionSystemCommentDTO> findCommentsById(Long id) {
    ResponseConstructionSystemDTO response = this.constructionSystemRepository
            .findById(id)
            .map(ConstructionSystemMapperUtils::toResponseCommentsDTO)
            .orElseThrow(() -> new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id));
    ;

    return response.getComments();
  }
}
