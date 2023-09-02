package com.optimal.standard.controller;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.service.MaterialService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@Validated
public class MaterialController {

  private final MaterialService materialService;

  @GetMapping("/user/material")
  public ResponseEntity<List<MaterialDTO>> findAll() {
    return ResponseEntity
        .ok()
        .body(this.materialService.findAll());
  }

  @GetMapping("/admin/material/{id}")
  public ResponseEntity<MaterialDTO> findById(@PathVariable Long id) {
    return ResponseEntity
        .ok()
        .body(this.materialService.findById(id));
  }

  @GetMapping("/admin/material/search")
  public ResponseEntity<List<MaterialDTO>> findAllByType(@RequestParam(value = "type") String type) {
    return ResponseEntity
        .ok()
        .body(this.materialService.findAllByType(type));
  }

  @PostMapping("/admin/material")
  public void create(@RequestBody @Valid MaterialDTO request) {
    this.materialService.saveMaterial(request);
  }

  @PutMapping("/admin/material/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid MaterialDTO request) {
    this.materialService.updateMaterial(id, request);
  }

  @DeleteMapping("/admin/material/{id}")
  public void delete(@PathVariable Long id) {
    this.materialService.deleteMaterial(id);
  }

}
