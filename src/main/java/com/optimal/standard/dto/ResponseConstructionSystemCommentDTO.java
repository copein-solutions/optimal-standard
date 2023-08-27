package com.optimal.standard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseConstructionSystemCommentDTO {

    private Long id;

    private String comment;

    private LocalDate date;

    private Long constructionSystemId;

    private String userName;

    private String userCompleteName;

}
