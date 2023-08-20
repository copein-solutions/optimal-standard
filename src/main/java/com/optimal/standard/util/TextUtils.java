package com.optimal.standard.util;

import java.text.Normalizer;

public interface TextUtils {

  static String sanitizeFileName(String input) {
    // Reemplazar espacios en blanco por guiones bajos
    String sanitized = input.replaceAll("\\s+", "_");

    // Eliminar caracteres especiales
    sanitized = sanitized.replaceAll("[^a-zA-Z0-9_.-]", "");

    // Normalizar caracteres a forma NFD y eliminar diacríticos
    sanitized = Normalizer
        .normalize(sanitized, Normalizer.Form.NFD)
        .replaceAll("\\p{M}", "");

    return sanitized;
  }

}
