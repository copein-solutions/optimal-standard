package com.optimal.standard.service;


import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.persistence.repository.MaterialRepository;
import com.optimal.standard.util.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.optimal.standard.util.MapperUtils.toMaterialMapper;

@Service
@AllArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    private final ApplicationAreaService applicationAreaService;

    public void saveMaterial(MaterialDTO request) {
        this.materialRepository.save(toMaterialMapper(request));
    }

    public List<ResponseMaterialDTO> findAll() {
        return this.materialRepository
                .findAll()
                .stream()
                .map(MapperUtils::toResponseDTO)
                .toList();
    }

}
