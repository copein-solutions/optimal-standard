package com.optimal.standard.util;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.optimal.standard.dto.ConstructionSystemMaterialDTO;
import com.optimal.standard.persistence.model.TypeOfUse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface SheetUtils {

  DecimalFormat decimalFormat = new DecimalFormat("#.00");

  String[] HEADERS =
      {"Id", "Campo de aplicación", "Costo materiales", "Material", "Tipo", "Precio unitario", "Composición", "Consumo total", "Manos",
          "Modo de aplicación", "Curado", "Malla 100%", "Precio unitario", "Malla parcial", "Precio unitario", "Coef. por m2",
          "Complemento del sistema 1", "Descripción", "Precio unitario", "Coef. por m2", "Descripción coef.", "Complemento del sistema 2",
          "Descripción", "Precio unitario", "Coef. por m2", "Descripción coef.", "Complemento del sistema 3", "Descripción",
          "Precio unitario", "Coef. por m2", "Descripción coef.", "Por área m2", "Vida útil (hrs)", "Temp. mínima de aplicación (°C)",
          "Otras", "Condiciones de base", "Condiciones de soporte"};

  static void createHeaders(XSSFSheet sheet, XSSFWorkbook workbook) {
    Font font = workbook.createFont();
    XSSFCellStyle style = workbook.createCellStyle();
    font.setBold(true);
    font.setFontHeightInPoints((short) 12);
    style.setFont(font);
    XSSFRow row = sheet.createRow(0);
    row.setRowStyle(style);

    for (int i = 0; i < HEADERS.length; i++) {
      sheet.setColumnWidth(i, (HEADERS[i].length() + 4) * 256);
      XSSFCell cell = row.createCell(i);
      cell.setCellStyle(style);
      cell.setCellValue(HEADERS[i]);
    }
    row
        .getCell(0)
        .getCellStyle()
        .setAlignment(HorizontalAlignment.LEFT);
  }

  static String getFormulaMaterialsCost(int row) {
    return "(F" + row + " * H" + row + ") + M" + row + " + (O" + row + " * P" + row + ") + (S" + row + " * T" + row + ") + (X" + row
        + " * Y" + row + ") + (AC" + row + " * AD" + row + ")";
  }

  static ConstructionSystemMaterialDTO fetchBaseMaterial(List<ConstructionSystemMaterialDTO> constructionSystems) {
    return emptyIfNull(constructionSystems)
        .stream()
        .filter(constructionSystem -> TypeOfUse.BASE.equals(constructionSystem.getTypeOfUse()))
        .findFirst()
        .orElse(null);
  }

  static ConstructionSystemMaterialDTO fetchTotalMesh(List<ConstructionSystemMaterialDTO> constructionSystems) {
    return emptyIfNull(constructionSystems)
        .stream()
        .filter(constructionSystem -> TypeOfUse.TOTAL_MESH.equals(constructionSystem.getTypeOfUse()))
        .findFirst()
        .orElse(null);
  }

  static ConstructionSystemMaterialDTO fetchPartialMesh(List<ConstructionSystemMaterialDTO> constructionSystems) {
    return emptyIfNull(constructionSystems)
        .stream()
        .filter(constructionSystem -> TypeOfUse.PARTIAL_MESH.equals(constructionSystem.getTypeOfUse()))
        .findFirst()
        .orElse(null);
  }

  static int getGridRow(XSSFRow dataRow) {
    return dataRow.getRowNum() + 1;
  }

  static double decimalFormatter(double number) {
    String formattedNumber = decimalFormat.format(number);
    try {
      return decimalFormat
          .parse(formattedNumber)
          .doubleValue();
    } catch (ParseException e) {
      return 0;
    }
  }

}
