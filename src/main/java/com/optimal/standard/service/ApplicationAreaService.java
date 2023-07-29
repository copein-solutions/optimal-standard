package com.optimal.standard.service;

import static com.optimal.standard.util.ApplicationAreaMapperUtils.toApplicationArea;
import static com.optimal.standard.util.ApplicationAreaMapperUtils.toDTO;

import com.optimal.standard.dto.ApplicationAreaDTO;
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
        .findById(id)
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
        .findAll()
        .stream()
        .map(ApplicationAreaMapperUtils::toDTO)
        .toList();
  }

  public ApplicationAreaDTO findById(Long id) {
    return this.applicationAreaRepository
        .findById(id)
        .map(ApplicationAreaMapperUtils::toDTO)
        .orElseThrow(() -> new EntityNotFoundException(APPLICATION_AREA_NOT_FOUND_MESSAGE + id));
  }

  public ApplicationArea findApplicationAreaById(Long id) {
    return this.applicationAreaRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(APPLICATION_AREA_NOT_FOUND_MESSAGE + id));
  }

  public boolean existById(Long id) {
    return this.applicationAreaRepository.existsById(id);
  }

}
