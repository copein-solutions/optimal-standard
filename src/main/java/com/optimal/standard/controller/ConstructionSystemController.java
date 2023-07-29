package com.optimal.standard.controller;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.service.ConstructionSystemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/construction_system")
public class ConstructionSystemController {

  private final ConstructionSystemService constructionSystemService;

  @GetMapping()
  public ResponseEntity<List<ConstructionSystemDTO>> findAll() {
    return ResponseEntity
            .ok()
            .body(this.constructionSystemService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ConstructionSystemDTO> findById(@PathVariable Long id) {
    return ResponseEntity
            .ok()
            .body(this.constructionSystemService.findById(id));
  }

  @PostMapping()
  public void create(@RequestBody @Valid ConstructionSystemDTO request) {
    this.constructionSystemService.saveConstructionSystem(request);
  }
}
