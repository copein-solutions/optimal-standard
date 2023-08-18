package com.optimal.standard.controller;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.service.MaterialService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/admin/material")
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping()
    public ResponseEntity<List<MaterialDTO>> findAll() {
        return ResponseEntity
                .ok()
                .body(this.materialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(this.materialService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MaterialDTO>> findAllByType(@RequestParam(value = "type") String type) {
        return ResponseEntity
                .ok()
                .body(this.materialService.findAllByType(type));
    }

    @PostMapping()
    public void create(@RequestBody @Valid MaterialDTO request) {
        this.materialService.saveMaterial(request);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid MaterialDTO request) {
        this.materialService.updateMaterial(id, request);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.materialService.deleteMaterial(id);
    }

}
