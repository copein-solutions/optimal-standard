package com.optimal.standard.controller;

import com.optimal.standard.dto.ConstructionSystemCommentDTO;
import com.optimal.standard.dto.ResponseConstructionSystemCommentDTO;
import com.optimal.standard.service.ConstructionSystemCommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ConstructionSystemCommentController {

    private final ConstructionSystemCommentService constructionSystemCommentService;

    @GetMapping("/user/construction_system/{id}/comment")
    public ResponseEntity<List<ResponseConstructionSystemCommentDTO>> findCommentsByIdUser(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(this.constructionSystemCommentService.findCommentsById(id, "USER"));
    }

    @GetMapping("/admin/construction_system/{id}/comment")
    public ResponseEntity<List<ResponseConstructionSystemCommentDTO>> findCommentsByIdAdmin(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(this.constructionSystemCommentService.findCommentsById(id, "ADMIN"));
    }

    @PostMapping("/user/construction_system/{id}/comment")
    public void createComment(@PathVariable Long id, @RequestBody @Valid ConstructionSystemCommentDTO request) {
        this.constructionSystemCommentService.saveConstructionSystemComment(id, request);
    }

    @PutMapping("/admin/construction_system/{id}/comment")
    public void updateComment(@PathVariable Long id, @RequestBody @Valid ConstructionSystemCommentDTO request) {
        this.constructionSystemCommentService.updateConstructionSystemComment(id, request);
    }

    @PutMapping("/admin/construction_system/comment_status/{id}/")
    public void statusComment(@PathVariable Long id, @RequestBody @Valid String request) {
        this.constructionSystemCommentService.setStatusConstructionSystemComment(id, request);
    }
}
