package com.optimal.standard.controller;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.service.ConstructionSystemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/construction_system")
public class ConstructionSystemController {

  private final ConstructionSystemService constructionSystemService;


  @PostMapping()
  public void create(@RequestBody @Valid ConstructionSystemDTO request) {
    this.constructionSystemService.saveConstructionSystem(request);
  }

}
