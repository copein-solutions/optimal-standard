package com.optimal.standard.controller;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.service.ApplicationAreaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("http://localhost:3000")
public class ApplicationAreaController {

  private final ApplicationAreaService applicationAreaService;

  @GetMapping("/application_areas")
  public ResponseEntity<List<ApplicationAreaDTO>> findAll() {
    return ResponseEntity
            .ok()
            .body(this.applicationAreaService.findAll());
  }

  @GetMapping("/application_area/{id}")
  public ResponseEntity<ApplicationAreaDTO> findById(@PathVariable Long id) {
    return ResponseEntity
            .ok()
            .body(this.applicationAreaService.findById(id));
  }

  @PostMapping("/application_area")
  public ResponseEntity<ApplicationAreaDTO> create(@RequestBody @Valid ApplicationAreaDTO request) {
    return ResponseEntity
            .ok()
            .body(this.applicationAreaService.saveApplicationArea(request));
  }

  @PutMapping("/application_area/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid ApplicationAreaDTO request) {
    this.applicationAreaService.updateApplicationArea(id, request);
  }
}
