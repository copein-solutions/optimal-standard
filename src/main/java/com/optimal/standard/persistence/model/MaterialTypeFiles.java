package com.optimal.standard.persistence.model;

public enum MaterialTypeFiles {

  TEMP("temp"),
  PERMANENT("permanent");

  private final String materialTypeFile;

  MaterialTypeFiles(String materialTypeFile) {
    this.materialTypeFile = materialTypeFile;
  }
}
