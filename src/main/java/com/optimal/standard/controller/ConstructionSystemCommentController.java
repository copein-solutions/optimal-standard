package com.optimal.standard.controller;

import static com.optimal.standard.util.AuthUtils.collectAuthorities;

import com.optimal.standard.dto.ConstructionSystemCommentDTO;
import com.optimal.standard.dto.ResponseConstructionSystemCommentDTO;
import com.optimal.standard.service.ConstructionSystemCommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/optimal_standard")
public class ConstructionSystemCommentController {

  private final ConstructionSystemCommentService constructionSystemCommentService;

  @GetMapping("/user/construction_system/{id}/comment")
  public ResponseEntity<List<ResponseConstructionSystemCommentDTO>> findCommentsByIdUser(@PathVariable Long id) {
    return ResponseEntity
        .ok()
        .body(this.constructionSystemCommentService.findCommentsById(id, collectAuthorities(SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getAuthorities())));
  }

  @PostMapping("/user/construction_system/{id}/comment")
  public void createComment(@PathVariable Long id, @RequestBody @Valid ConstructionSystemCommentDTO request) {
    this.constructionSystemCommentService.saveConstructionSystemComment(id, request);
  }

  @PutMapping("/admin/construction_system/{id}/comment")
  public void updateComment(@PathVariable Long id, @RequestBody @Valid ConstructionSystemCommentDTO request) {
    this.constructionSystemCommentService.updateConstructionSystemComment(id, request);
  }

  @PutMapping("/admin/construction_system/comment_status/{id}")
  public void statusComment(@PathVariable Long id, @RequestBody @Valid ConstructionSystemCommentDTO request) {
    this.constructionSystemCommentService.setStatusConstructionSystemComment(id, request);
  }

}
