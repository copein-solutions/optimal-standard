package com.optimal.standard.controller;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.service.ApplicationAreaService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/optimal_standard")
public class ApplicationAreaController {

  private final ApplicationAreaService applicationAreaService;

  @GetMapping("/user/application_area")
  public ResponseEntity<List<ApplicationAreaDTO>> findAll() {
    return ResponseEntity
        .ok()
        .body(this.applicationAreaService.findAll());
  }

  @GetMapping("/admin/application_area/{id}")
  public ResponseEntity<ApplicationAreaDTO> findById(@PathVariable Long id) {
    return ResponseEntity
        .ok()
        .body(this.applicationAreaService.findById(id));
  }

  @PostMapping("/admin/application_area")
  public ResponseEntity<ApplicationAreaDTO> create(@RequestBody @Valid ApplicationAreaDTO request) {
    return ResponseEntity
        .ok()
        .body(this.applicationAreaService.saveApplicationArea(request));
  }

  @PutMapping("/admin/application_area/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid ApplicationAreaDTO request) {
    this.applicationAreaService.updateApplicationArea(id, request);
  }

  @DeleteMapping("/admin/application_area/{id}")
  public void delete(@PathVariable Long id) {
    this.applicationAreaService.deleteApplicationArea(id);
  }

}
