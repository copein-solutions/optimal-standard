package com.optimal.standard.service;

import com.optimal.standard.dto.ConstructionSystemCommentDTO;
import com.optimal.standard.dto.ResponseConstructionSystemCommentDTO;
import com.optimal.standard.persistence.model.CommentStatus;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.ConstructionSystemComment;
import com.optimal.standard.persistence.repository.ConstructionSystemCommentRepository;
import com.optimal.standard.persistence.repository.ConstructionSystemRepository;
import com.optimal.standard.persistence.repository.UserRepository;
import com.optimal.standard.util.ConstructionSystemMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConstructionSystemCommentService {

  public static final String ROLE_ADMIN = "ROLE_ADMIN";

  public static final String CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE = "Construction system not found with ID: ";

  public static final String CONSTRUCTION_SYSTEM_COMMENT_NOT_FOUND_MESSAGE = "Construction system comment not found with ID: ";

  private final ConstructionSystemRepository constructionSystemRepository;

  private final ConstructionSystemCommentRepository constructionSystemCommentRepository;

  private final UserRepository userRepository;

  public void saveConstructionSystemComment(Long id, ConstructionSystemCommentDTO request) {
    this.constructionSystemRepository
        .findByIdAndDeletedFalse(id)
        .ifPresentOrElse(constructionSystemDatabase -> {
          Authentication authentication = SecurityContextHolder
              .getContext()
              .getAuthentication();
          String username = authentication.getName();

          this.constructionSystemCommentRepository.save(
              new ConstructionSystemComment(request.getId(), request.getComment(), LocalDate.now(), CommentStatus.PENDING.name(),
                  constructionSystemDatabase, this.userRepository
                  .findByUsername(username)
                  .get()));
        }, () -> {
          throw new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id);
        });
  }

  public void updateConstructionSystemComment(Long id, ConstructionSystemCommentDTO request) {
    this.constructionSystemRepository
        .findByIdAndDeletedFalse(id)
        .ifPresentOrElse(constructionSystemDatabase -> {
          ConstructionSystemComment constructionSystemComment = this.constructionSystemCommentRepository
              .findById(request.getId())
              .orElseThrow(() -> {
                throw new EntityNotFoundException(CONSTRUCTION_SYSTEM_COMMENT_NOT_FOUND_MESSAGE + id);
              });

          constructionSystemComment.setComment(request.getComment());
          this.constructionSystemCommentRepository.save(constructionSystemComment);
        }, () -> {
          throw new EntityNotFoundException(CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE + id);
        });
  }

  public List<ResponseConstructionSystemCommentDTO> findCommentsById(Long id, List<String> authorities) {
    List<String> status = new ArrayList<>();
    status.add(CommentStatus.VALIDATED.name());
    if (authorities.contains(ROLE_ADMIN)) {
      status.add(CommentStatus.PENDING.name());
    }
    return this.constructionSystemCommentRepository
        .findByConstructionSystemIdAndStatusIn(id, status)
        .stream()
        .map(ConstructionSystemMapperUtils::toConstructionSystemComment)
        .toList();
  }

  public void setStatusConstructionSystemComment(Long id, ConstructionSystemCommentDTO request) {
    if (request
        .getStatus()
        .equals(CommentStatus.REJECTED.name())) {
      this.deleteConstructionSystemComment(id);
    } else {
      ConstructionSystemComment constructionSystemComment = this.constructionSystemCommentRepository
          .findById(id)
          .orElseThrow(() -> new EntityNotFoundException(CONSTRUCTION_SYSTEM_COMMENT_NOT_FOUND_MESSAGE + id));
      constructionSystemComment.setStatus(CommentStatus.VALIDATED.name());

      this.constructionSystemCommentRepository.save(constructionSystemComment);
    }
  }

  public void deleteConstructionSystemComment(Long id) {
    ConstructionSystemComment constructionSystemComment = this.constructionSystemCommentRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(CONSTRUCTION_SYSTEM_COMMENT_NOT_FOUND_MESSAGE + id));

    ConstructionSystem constructionSystem = constructionSystemComment.getConstructionSystem();
    constructionSystem
        .getConstructionSystemComments()
        .remove(constructionSystemComment);
    this.constructionSystemRepository.save(constructionSystem);
  }

//  private List<ResponseConstructionSystemCommentDTO> toConstructionSystemComments(
//      List<ConstructionSystemComment> constructionSystemComments) {
//    return constructionSystemComments
//        .stream()
//        .filter(cs -> cs
//            .getStatus()
//            .equals(CommentStatus.VALIDATED.name()))
//        .sorted(Comparator.comparing(ConstructionSystemComment::getCreatedDate, Comparator.reverseOrder()))
//        .limit(5)
//        .map(this::toConstructionSystemComment)
//        .collect(Collectors.toList());
//  }
//
//  private ResponseConstructionSystemCommentDTO toConstructionSystemComment(ConstructionSystemComment constructionSystemComment) {
//    return ResponseConstructionSystemCommentDTO
//        .builder()
//        .id(constructionSystemComment.getId())
//        .comment(constructionSystemComment.getComment())
//        .date(constructionSystemComment.getCreatedDate())
//        .constructionSystemId(constructionSystemComment
//            .getConstructionSystem()
//            .getId())
//        .userName(constructionSystemComment
//            .getRegisteredUser()
//            .getUsername())
//        .userCompleteName(constructionSystemComment
//            .getRegisteredUser()
//            .getName())
//        .status(constructionSystemComment.getStatus())
//        .build();
//  }
//
//  private List<ResponseConstructionSystemCommentDTO> toConstructionSystemAllComments(
//      List<ConstructionSystemComment> constructionSystemComments) {
//    return constructionSystemComments
//        .stream()
//        .sorted(Comparator.comparing(ConstructionSystemComment::getCreatedDate, Comparator.reverseOrder()))
//        .map(this::toConstructionSystemComment)
//        .collect(Collectors.toList());
//  }

}
