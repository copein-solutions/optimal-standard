package com.optimal.standard.controller;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.dto.SystemCategoryDTO;
import com.optimal.standard.service.ConstructionSystemService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ConstructionSystemController {

  private final ConstructionSystemService constructionSystemService;

  @GetMapping("/user/construction_system")
  public ResponseEntity<List<ResponseConstructionSystemDTO>> findAll() {
    return ResponseEntity
        .ok()
        .body(this.constructionSystemService.findAll());
  }

  @GetMapping("/public/user/construction_system/export-xls")
  public ResponseEntity<byte[]> exportXls() throws Exception, ChangeSetPersister.NotFoundException {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
    var contentDisposition = ContentDisposition
        .builder("attachment")
        .filename("optimalStandard_Report" + ".xls")
        .build();
    headers.setContentDisposition(contentDisposition);
    return ResponseEntity
        .ok()
        .headers(headers)
        .body(this.constructionSystemService.exportXls());
  }

  @GetMapping("/user/construction_system/{id}")
  public ResponseEntity<ResponseConstructionSystemDTO> findById(@PathVariable Long id) {
    return ResponseEntity
        .ok()
        .body(this.constructionSystemService.findById(id));
  }

  @PostMapping("/admin/construction_system")
  public void create(@RequestBody @Valid ConstructionSystemDTO request) {
    this.constructionSystemService.saveConstructionSystem(request);
  }

  @PutMapping("/admin/construction_system/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid ConstructionSystemDTO request) {
    this.constructionSystemService.updateConstructionSystem(id, request);
  }

  @PutMapping("/admin/construction_system/{id}/stdo")
  public void updateOptimalStandard(@PathVariable Long id, @RequestBody @Valid SystemCategoryDTO request) {
    this.constructionSystemService.updateSystemCategory(id, request);
  }

  @DeleteMapping("/admin/construction_system/{id}")
  public void delete(@PathVariable Long id) {
    this.constructionSystemService.deleteConstructionSystem(id);
  }


}
