package com.optimal.standard.service;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.repository.ApplicationAreaRepository;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import static com.optimal.standard.util.MapperUtils.toMaterialMapper;

@Service
@AllArgsConstructor
public class ApplicationAreaService {

  private static final String APPLICATION_AREA_NOT_FOUND_MESSAGE = "Application area not found with ID: ";

  private final ApplicationAreaRepository applicationAreaRepository;

  private static ApplicationArea toApplicationAreaMapper(ApplicationAreaDTO applicationAreaDTO) {
    return ApplicationArea
        .builder()
        .name(applicationAreaDTO.getName())
        .specification(applicationAreaDTO.getSpecification())
        .considerations(applicationAreaDTO.getConsiderations())
        .build();
  }

  private static ApplicationAreaDTO toDTO(ApplicationArea applicationArea) {
    return ApplicationAreaDTO
        .builder()
        .name(applicationArea.getName())
        .specification(applicationArea.getSpecification())
        .considerations(applicationArea.getConsiderations())
        .build();
  }

  public ApplicationAreaDTO saveApplicationArea(ApplicationAreaDTO request) {
    ApplicationArea applicationArea = this.applicationAreaRepository.save(toApplicationAreaMapper(request));
    return toDTO(applicationArea);
  }

  public void updateApplicationArea(Long id, ApplicationAreaDTO request) {
    this.applicationAreaRepository
            .findById(id)
            .ifPresentOrElse(applicationAreaDatabase -> {
              ApplicationArea applicationArea = toApplicationAreaMapper(request);
              applicationArea.setId(applicationAreaDatabase.getId());
              this.applicationAreaRepository.save(applicationArea);
            }, () -> {
              throw new EntityNotFoundException(APPLICATION_AREA_NOT_FOUND_MESSAGE + id);
            });
  }

  @SneakyThrows
  public List<ApplicationArea> findApplicationAreaByIds(List<Long> ids) {
    return Optional
        .of(this.applicationAreaRepository.findAllById(ids))
        .orElseThrow(NotFoundException::new);
  }

  public List<ApplicationAreaDTO> findAll() {
    return this.applicationAreaRepository
        .findAll()
        .stream()
        .map(ApplicationAreaService::toDTO)
        .toList();
  }

}
