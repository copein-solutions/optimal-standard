package com.optimal.standard.service;

import static com.optimal.standard.service.ApplicationAreaService.APPLICATION_AREA_NOT_FOUND_MESSAGE;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.TypeOfUseOfMaterial;
import com.optimal.standard.exception.BadRequestException;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConstructionSystemService {

  private final ApplicationAreaService applicationAreaService;

  private final MaterialService materialService;

  public void saveConstructionSystem(ConstructionSystemDTO request) {
    this.validateApplicationArea(request.getApplicationAreaId());
    this.validateTypeOfUseOfMaterials(request.getMaterials());

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

}
