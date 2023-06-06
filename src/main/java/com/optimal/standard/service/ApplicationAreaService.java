package com.optimal.standard.service;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.repository.ApplicationAreaRepository;
import com.optimal.standard.util.MapperUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationAreaService {

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
        .materials(applicationArea
            .getMaterials()
            .stream()
            .map(MapperUtils::toDTO)
            .collect(Collectors.toSet()))
        .build();
  }

  public ApplicationAreaDTO saveApplicationArea(ApplicationAreaDTO request) {
    ApplicationArea applicationArea = this.applicationAreaRepository.save(toApplicationAreaMapper(request));
    return toDTO(applicationArea);
  }

  @SneakyThrows
  public List<ApplicationArea> findApplicationAreaById(List<Long> ids) {
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
