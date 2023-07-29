package com.optimal.standard.persistence.model;

public enum TypeOfUse {

  BASE("base"),
  PARTIAL_MESH("partial_mesh"),
  TOTAL_MESH("total_mesh"),
  PLUGIN_MATERIAL("plugin_material");

  private final String typeOfUse;

  TypeOfUse(String typeOfUse) {
    this.typeOfUse = typeOfUse;
  }
}
