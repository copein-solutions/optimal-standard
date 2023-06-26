package com.optimal.standard.controller;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.dto.ResponseMaterialDTO;
import com.optimal.standard.service.MaterialService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@Validated
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
