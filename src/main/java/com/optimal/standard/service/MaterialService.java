package com.optimal.standard.service;


import static com.optimal.standard.util.MapperUtils.toMaterialMapper;

import com.optimal.standard.dto.CreateMaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.Material;
import com.optimal.standard.persistence.repository.MaterialRepository;
import com.optimal.standard.util.MapperUtils;
import java.util.HashSet;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaterialService {

  private final MaterialRepository materialRepository;

  private final ApplicationAreaService applicationAreaService;

  public void saveMaterial(CreateMaterialDTO request) {
    List<ApplicationArea> applicationAreas = this.applicationAreaService.findApplicationAreaByIds(request.getApplicationAreaIds());
    Material material = toMaterialMapper(request, new HashSet<>(applicationAreas));
    this.materialRepository.save(material);
  }

  public List<ResponseMaterialDTO> findAll() {
    return this.materialRepository
        .findAll()
        .stream()
        .map(MapperUtils::toResponseDTO)
        .toList();
  }

}
