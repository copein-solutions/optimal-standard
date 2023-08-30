package com.optimal.standard.service;

import com.optimal.standard.dto.ConstructionSystemCommentDTO;
import com.optimal.standard.dto.ResponseConstructionSystemCommentDTO;
import com.optimal.standard.persistence.model.ConstructionSystemComment;
import com.optimal.standard.persistence.repository.ConstructionSystemCommentRepository;
import com.optimal.standard.persistence.repository.ConstructionSystemRepository;
import com.optimal.standard.persistence.repository.UserRepository;
import com.optimal.standard.util.ConstructionSystemMapperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ConstructionSystemCommentService {

    public static final String CONSTRUCTION_SYSTEM_NOT_FOUND_MESSAGE = "Construction system not found with ID: ";
    public static final String CONSTRUCTION_SYSTEM_COMMENT_NOT_FOUND_MESSAGE = "Construction system comment not found with ID: ";

    private final ConstructionSystemRepository constructionSystemRepository;

    private final ConstructionSystemCommentRepository constructionSystemCommentRepository;

    private final UserRepository userRepository;

    public void saveConstructionSystemComment(Long id, ConstructionSystemCommentDTO request) {
        this.constructionSystemRepository
                .findByIdAndDeletedFalse(id)
                .ifPresentOrElse(constructionSystemDatabase -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    String username = authentication.getName();

                    this.constructionSystemCommentRepository.save(new ConstructionSystemComment(
                            request.getId(),
                            request.getComment(),
                            LocalDate.now(),
                            "PENDING",
                            constructionSystemDatabase,
                            this.userRepository.findByUsername(username)
                    ));
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

    public List<ResponseConstructionSystemCommentDTO> findCommentsById(Long id, String user) {
        List<String> status = new ArrayList<>();
        status.add("VALIDATED");
        if (user.equals("ADMIN")) {
            status.add("PENDING");
        }
        return this.constructionSystemCommentRepository
                .findByConstructionSystemIdAndStatusIn(id, status)
                .stream()
                .map(ConstructionSystemMapperUtils::toConstructionSystemComment)
                .toList();
    }

    public void setStatusConstructionSystemComment(Long id, String request) {
        if (request.equals("REJECTED")) {
            this.constructionSystemCommentRepository.deleteById(id);
        } else {
            ConstructionSystemComment constructionSystemComment = this.constructionSystemCommentRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(CONSTRUCTION_SYSTEM_COMMENT_NOT_FOUND_MESSAGE + id));
            constructionSystemComment.setStatus("VALIDATED");

            this.constructionSystemCommentRepository.save(constructionSystemComment);
        }
    }
}
