package com.optimal.standard.controller;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("http://localhost:3000")
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping("/material")
    public void create(@RequestBody @Valid MaterialDTO request) {
        this.materialService.saveMaterial(request);
    }

    @GetMapping("/materials")
    public List<ResponseMaterialDTO> findAll() {
        return this.materialService.findAll();
    }

}
