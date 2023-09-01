package com.optimal.standard.persistence.model;

public enum SystemCategory {

    OPTIMAL_STANDARD("optimal_standard"),
    ALTERNATIVE_OPTIMAL_STANDARD("alternative_optimal_standard");

    private final String systemCategory;

    SystemCategory(String systemCategory) {
        this.systemCategory = systemCategory;
    }
}
