package com.optimal.standard.service;


import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.persistence.repository.MaterialRepository;
import com.optimal.standard.util.MapperUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaterialService {

  private final MaterialRepository materialRepository;

  private final ApplicationAreaService applicationAreaService;

  public void saveMaterial(MaterialDTO request) {
//    List<ApplicationArea> applicationAreas = this.applicationAreaService.findApplicationAreaById(request
//        .getApplicationAreas()
//        .stream()
//        .map(a));
//    Material material = toMaterialMapper(request, new HashSet<>(applicationAreas));
//    this.materialRepository.save(material);
  }

  public List<MaterialDTO> findAll() {
    return this.materialRepository
        .findAll()
        .stream()
        .map(MapperUtils::toDTO)
        .toList();
  }

}
