package com.optimal.standard.controller;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.service.ApplicationAreaService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@CrossOrigin("http://localhost:3000")
public class ApplicationAreaController {

  private final ApplicationAreaService applicationAreaService;

  @PostMapping("/application_area")
  public ApplicationAreaDTO create(@RequestBody @Valid ApplicationAreaDTO request) {
    return this.applicationAreaService.saveApplicationArea(request);
  }

  @GetMapping("/application_areas")
  public List<ApplicationAreaDTO> findAll() {
    return this.applicationAreaService.findAll();
  }

}
