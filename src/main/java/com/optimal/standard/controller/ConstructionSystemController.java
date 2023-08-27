package com.optimal.standard.controller;

import com.optimal.standard.dto.*;
import com.optimal.standard.service.ConstructionSystemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/construction_system/{id}")
    public ResponseEntity<ResponseConstructionSystemDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(this.constructionSystemService.findById(id));
    }

    @GetMapping("/user/construction_system/{id}/comment")
    public ResponseEntity<List<ResponseConstructionSystemCommentDTO>> findCommentsById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(this.constructionSystemService.findCommentsById(id));
    }

    @PostMapping("/admin/construction_system")
    public void create(@RequestBody @Valid ConstructionSystemDTO request) {
        this.constructionSystemService.saveConstructionSystem(request);
    }

    @PostMapping("/user/construction_system/{id}/comment")
    public void createComment(@PathVariable Long id, @RequestBody @Valid ConstructionSystemCommentDTO request) {
        this.constructionSystemService.saveConstructionSystemComment(id, request);
    }

    @PutMapping("/user/construction_system/{id}/comment")
    public void updateComment(@PathVariable Long id, @RequestBody @Valid ConstructionSystemCommentDTO request) {
        this.constructionSystemService.updateConstructionSystemComment(id, request);
    }

    @PutMapping("/admin/construction_system/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid ConstructionSystemDTO request) {
        this.constructionSystemService.updateConstructionSystem(id, request);
    }

    @PutMapping("/admin/construction_system/{id}/stdo")
    public void updateOptimalStandard(@PathVariable Long id, @RequestBody @Valid SystemCategoryDTO request) {
        this.constructionSystemService.updateSystemCategory(id, request);
    }

//  @DeleteMapping("/admin/construction_system/{id}")
//  public void delete(@PathVariable Long id) {
//    this.constructionSystemService.deleteConstructionSystem(id);
//  }

    @DeleteMapping("/user/construction_system/comment/{id}")
    public void deleteComment(@PathVariable Long id) {
        this.constructionSystemService.deleteConstructionSystemComment(id);
    }
}
