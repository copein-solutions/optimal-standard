package com.optimal.standard.persistence.model;

public enum CommentStatus {

    VALIDATED("validated"),
    PENDING("pending"),
    REJECTED("rejected");

    private final String commentStatus;

    CommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }
}
