package com.optimal.standard.service;

import static com.optimal.standard.util.ApplicationAreaMapperUtils.toApplicationArea;
import static com.optimal.standard.util.ApplicationAreaMapperUtils.toDTO;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.exception.BadRequestException;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.repository.ApplicationAreaRepository;
import com.optimal.standard.util.ApplicationAreaMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationAreaService {

  public static final String APPLICATION_AREA_NOT_FOUND_MESSAGE = "Application area not found with ID: ";

  private final ApplicationAreaRepository applicationAreaRepository;


  public ApplicationAreaDTO saveApplicationArea(ApplicationAreaDTO request) {
    ApplicationArea applicationArea = this.applicationAreaRepository.save(toApplicationArea(request));
    return toDTO(applicationArea);
  }

  public void updateApplicationArea(Long id, ApplicationAreaDTO request) {
    this.applicationAreaRepository
        .findByIdAndDeletedFalse(id)
        .ifPresentOrElse(applicationAreaDatabase -> {
          ApplicationArea applicationArea = toApplicationArea(request);
          applicationArea.setId(applicationAreaDatabase.getId());
          this.applicationAreaRepository.save(applicationArea);
        }, () -> {
          throw new EntityNotFoundException(APPLICATION_AREA_NOT_FOUND_MESSAGE + id);
        });
  }

  public List<ApplicationAreaDTO> findAll() {
    return this.applicationAreaRepository
        .findAllByDeletedFalse()
        .stream()
        .map(ApplicationAreaMapperUtils::toDTO)
        .toList();
  }

  public ApplicationAreaDTO findById(Long id) {
    return this.applicationAreaRepository
        .findByIdAndDeletedFalse(id)
        .map(ApplicationAreaMapperUtils::toDTO)
        .orElseThrow(() -> new EntityNotFoundException(APPLICATION_AREA_NOT_FOUND_MESSAGE + id));
  }

  public ApplicationArea findApplicationAreaById(Long id) {
    return this.findApplicationAreaEntityById(id);
  }

  private ApplicationArea findApplicationAreaEntityById(Long id) {
    return this.applicationAreaRepository
        .findByIdAndDeletedFalse(id)
        .orElseThrow(() -> new EntityNotFoundException(APPLICATION_AREA_NOT_FOUND_MESSAGE + id));
  }

  public boolean existById(Long id) {
    return this.applicationAreaRepository.existsByIdAndDeletedFalse(id);
  }

  public void deleteApplicationArea(Long id) {
    ApplicationArea applicationArea = this.findApplicationAreaById(id);
    if (isNotEmpty(applicationArea.getConstructionSystems())) {
      throw new BadRequestException(
          "Deleting the ApplicationArea: " + id + " is not possible due to references from a Construction System.");
    }
    this.applicationAreaRepository.markAsDeleted(id);
  }

}
