package com.optimal.standard.controller;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.service.ConstructionSystemService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<ResponseConstructionSystemDTO> findById(@PathVariable Long id) {
    return ResponseEntity
        .ok()
        .body(this.constructionSystemService.findById(id));
  }

  @PostMapping()
  public void create(@RequestBody @Valid ConstructionSystemDTO request) {
    this.constructionSystemService.saveConstructionSystem(request);
  }

  @PutMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid ConstructionSystemDTO request) {
    this.constructionSystemService.updateConstructionSystem(id, request);
  }

}
