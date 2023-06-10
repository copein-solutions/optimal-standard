package com.optimal.standard.controller;

import com.optimal.standard.dto.CreateMaterialDTO;
import com.optimal.standard.dto.ModifyMaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping("/material")
    public void create(@RequestBody @Valid CreateMaterialDTO request) {
        this.materialService.saveMaterial(request);
    }

    @GetMapping("/materials")
    public List<ResponseMaterialDTO> findAll() {
        return this.materialService.findAll();
    }

    @PutMapping("/material")
    public void update(@RequestBody @Valid ModifyMaterialDTO request) {
        this.materialService.updateMaterial(request);
    }

}
