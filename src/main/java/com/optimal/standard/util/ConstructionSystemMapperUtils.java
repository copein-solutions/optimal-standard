package com.optimal.standard.util;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.ConstructionSystemMaterialDTO;
import com.optimal.standard.dto.ResponseConstructionSystemCommentDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.CommentStatus;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.ConstructionSystemComment;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface ConstructionSystemMapperUtils {

  static ConstructionSystem toConstructionSystem(ConstructionSystemDTO constructionSystem, ApplicationArea applicationArea) {
    return ConstructionSystem
        .builder()
        .id(constructionSystem.getId())
        .totalConsumption(constructionSystem.getTotalConsumption())
        .layers(constructionSystem.getLayers())
        .applicationMode(constructionSystem.getApplicationMode())
        .cured(constructionSystem.isCured())
        .applicationArea(applicationArea)
        .baseConditions(constructionSystem.getBaseConditions())
        .supportConditions(constructionSystem.getSupportConditions())
        .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
        .materialAreaDescription(constructionSystem.getMaterialAreaDescription())
        .build();
  }

  static ResponseConstructionSystemDTO toResponseDTO(ConstructionSystem constructionSystem, double totalPrice,
      List<ConstructionSystemMaterialDTO> constructionSystemMaterials) {
    return ResponseConstructionSystemDTO
        .builder()
        .id(constructionSystem.getId())
        .totalConsumption(constructionSystem.getTotalConsumption())
        .layers(constructionSystem.getLayers())
        .applicationMode(constructionSystem.getApplicationMode())
        .cured(constructionSystem.isCured())
        .totalPrice(totalPrice)
        .baseConditions(constructionSystem.getBaseConditions())
        .supportConditions(constructionSystem.getSupportConditions())
        .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
        .materialAreaDescription(constructionSystem.getMaterialAreaDescription())
        .applicationArea(ApplicationAreaMapperUtils.toDTO(constructionSystem.getApplicationArea()))
        .materials(constructionSystemMaterials)
        .comments(toConstructionSystemComments(constructionSystem.getConstructionSystemComments()))
        .systemCategory(constructionSystem.getSystemCategory())
        .build();
  }

  static List<ResponseConstructionSystemCommentDTO> toConstructionSystemComments(
      List<ConstructionSystemComment> constructionSystemComments) {
    return constructionSystemComments
        .stream()
        .filter(cs -> cs
            .getStatus()
            .equals(CommentStatus.VALIDATED.name()))
        .sorted(Comparator.comparing(ConstructionSystemComment::getCreatedDate, Comparator.reverseOrder()))
        .limit(5)
        .map(ConstructionSystemMapperUtils::toConstructionSystemComment)
        .collect(Collectors.toList());
  }

  static ResponseConstructionSystemCommentDTO toConstructionSystemComment(ConstructionSystemComment constructionSystemComment) {
    return ResponseConstructionSystemCommentDTO
        .builder()
        .id(constructionSystemComment.getId())
        .comment(constructionSystemComment.getComment())
        .date(constructionSystemComment.getCreatedDate())
        .constructionSystemId(constructionSystemComment
            .getConstructionSystem()
            .getId())
        .userName(constructionSystemComment
            .getRegisteredUser()
            .getUsername())
        .userCompleteName(constructionSystemComment
            .getRegisteredUser()
            .getName())
        .status(constructionSystemComment.getStatus())
        .build();
  }

  static List<ResponseConstructionSystemCommentDTO> toConstructionSystemAllComments(
      List<ConstructionSystemComment> constructionSystemComments) {
    return constructionSystemComments
        .stream()
        .sorted(Comparator.comparing(ConstructionSystemComment::getCreatedDate, Comparator.reverseOrder()))
        .map(ConstructionSystemMapperUtils::toConstructionSystemComment)
        .collect(Collectors.toList());
  }

}
