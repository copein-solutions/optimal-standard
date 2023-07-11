package com.optimal.standard.util;

import com.optimal.standard.dto.ApplicationAreaDTO;
import com.optimal.standard.persistence.model.ApplicationArea;

public interface ApplicationAreaMapperUtils {

  static ApplicationArea toApplicationArea(ApplicationAreaDTO applicationArea) {
    return ApplicationArea
        .builder()
        .id(applicationArea.getId())
        .name(applicationArea.getName())
        .considerations(applicationArea.getConsiderations())
        .build();
  }

  static ApplicationAreaDTO toDTO(ApplicationArea applicationArea) {
    return ApplicationAreaDTO
        .builder()
        .id(applicationArea.getId())
        .name(applicationArea.getName())
        .considerations(applicationArea.getConsiderations())
        .build();
  }

}
