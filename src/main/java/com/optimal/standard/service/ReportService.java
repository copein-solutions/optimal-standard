package com.optimal.standard.service;

import static com.optimal.standard.util.SheetUtils.createHeaders;
import static com.optimal.standard.util.SheetUtils.decimalFormatter;
import static com.optimal.standard.util.SheetUtils.fetchBaseMaterial;
import static com.optimal.standard.util.SheetUtils.fetchPartialMesh;
import static com.optimal.standard.util.SheetUtils.fetchTotalMesh;
import static com.optimal.standard.util.SheetUtils.getFormulaMaterialsCost;
import static com.optimal.standard.util.SheetUtils.getGridRow;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import com.optimal.standard.dto.ConstructionSystemMaterialDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.persistence.model.TypeOfUse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportService {

  private static String SHEET_NAME = "Report";

  private final ConstructionSystemService constructionSystemService;

  private void createPluginsCells(ResponseConstructionSystemDTO cs, XSSFRow dataRow) {
    AtomicInteger dataRowForPluginsIndex = new AtomicInteger(16);
    emptyIfNull(cs.getMaterials()).forEach(csm -> {
      if (TypeOfUse.PLUGIN_MATERIAL.equals(csm.getTypeOfUse())) {
        dataRow
            .createCell(dataRowForPluginsIndex.get())
            .setCellValue(csm
                .getMaterial()
                .getProduct());
        dataRowForPluginsIndex.getAndIncrement();
        dataRow
            .createCell(dataRowForPluginsIndex.get())
            .setCellValue(csm.getMaterialDescription());
        dataRowForPluginsIndex.getAndIncrement();
        dataRow
            .createCell(dataRowForPluginsIndex.get())
            .setCellValue(decimalFormatter(csm
                .getMaterial()
                .getUnitPrice()));
        dataRowForPluginsIndex.getAndIncrement();
        dataRow
            .createCell(dataRowForPluginsIndex.get())
            .setCellValue(csm.getCoefficient());
        dataRowForPluginsIndex.getAndIncrement();
        dataRow
            .createCell(dataRowForPluginsIndex.get())
            .setCellValue(csm.getCoefficientDescription());
        dataRowForPluginsIndex.getAndIncrement();
      }
    });
  }

  public ByteArrayInputStream generateXlsx() throws IOException {
    List<ResponseConstructionSystemDTO> constructionSystems = this.constructionSystemService.findAll();
    XSSFWorkbook workbook = new XSSFWorkbook();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    XSSFSheet sheet = workbook.createSheet(SHEET_NAME);
    createHeaders(sheet, workbook);

    AtomicInteger dataRowIndex = new AtomicInteger(1);
    for (ResponseConstructionSystemDTO cs : constructionSystems) {
      XSSFRow dataRow = sheet.createRow(dataRowIndex.get());

      XSSFCell cellForColumn0 = dataRow.createCell(0);
      cellForColumn0.setCellValue(cs.getId());
      cellForColumn0
          .getCellStyle()
          .setAlignment(HorizontalAlignment.LEFT);
      dataRow
          .createCell(1)
          .setCellValue(cs
              .getApplicationArea()
              .getName());

      XSSFCell cellForColumn2 = dataRow.createCell(2);
      String formulaMaterialsCost = getFormulaMaterialsCost(getGridRow(dataRow));
      cellForColumn2.setCellFormula(formulaMaterialsCost);

      ConstructionSystemMaterialDTO constructionSystemBaseMaterial = this.createBaseMaterialCells(cs, dataRow);

      dataRow
          .createCell(7)
          .setCellValue(cs.getTotalConsumption());
      dataRow
          .createCell(8)
          .setCellValue(cs.getLayers());
      dataRow
          .createCell(9)
          .setCellValue(cs.getApplicationMode());
      dataRow
          .createCell(10)
          .setCellValue(cs.isCured() ? "SI" : "NO");

      this.createTotalMeshCells(cs, dataRow);
      this.createPartialMeshCells(cs, dataRow);
      this.createPluginsCells(cs, dataRow);
      int indexAfterPlugins = 31;
      // Restrictions
      dataRow
          .createCell(indexAfterPlugins)
          .setCellValue(cs.getMaterialAreaRestrictions());
      indexAfterPlugins++;
      dataRow
          .createCell(indexAfterPlugins)
          .setCellValue(constructionSystemBaseMaterial
              .getMaterial()
              .getPotLife());
      indexAfterPlugins++;
      dataRow
          .createCell(indexAfterPlugins)
          .setCellValue(constructionSystemBaseMaterial
              .getMaterial()
              .getMinApplicableTemp());
      indexAfterPlugins++;
      dataRow
          .createCell(indexAfterPlugins)
          .setCellValue(cs.getMaterialAreaDescription());
      indexAfterPlugins++;
      dataRow
          .createCell(indexAfterPlugins)
          .setCellValue(cs.getBaseConditions());
      indexAfterPlugins++;
      dataRow
          .createCell(indexAfterPlugins)
          .setCellValue(cs.getSupportConditions());
      dataRowIndex.getAndIncrement();
    }

    workbook.write(out);
    return new ByteArrayInputStream(out.toByteArray());
  }

  private ConstructionSystemMaterialDTO createBaseMaterialCells(ResponseConstructionSystemDTO cs, XSSFRow dataRow) {
    ConstructionSystemMaterialDTO constructionSystemBaseMaterial = fetchBaseMaterial(cs.getMaterials());
    if (Objects.nonNull(constructionSystemBaseMaterial)) {
      dataRow
          .createCell(3)
          .setCellValue(constructionSystemBaseMaterial
              .getMaterial()
              .getProduct());
      dataRow
          .createCell(4)
          .setCellValue(constructionSystemBaseMaterial
              .getMaterial()
              .getType());
      dataRow
          .createCell(5)
          .setCellValue(decimalFormatter(constructionSystemBaseMaterial
              .getMaterial()
              .getUnitPrice()));
      dataRow
          .createCell(6)
          .setCellValue(constructionSystemBaseMaterial
              .getMaterial()
              .getComponent());
    }
    return constructionSystemBaseMaterial;
  }

  private void createTotalMeshCells(ResponseConstructionSystemDTO cs, XSSFRow dataRow) {
    ConstructionSystemMaterialDTO constructionSystemTotalMesh = fetchTotalMesh(cs.getMaterials());
    if (Objects.nonNull(constructionSystemTotalMesh)) {
      dataRow
          .createCell(11)
          .setCellValue(constructionSystemTotalMesh
              .getMaterial()
              .getProduct());
      dataRow
          .createCell(12)
          .setCellValue(decimalFormatter(constructionSystemTotalMesh
              .getMaterial()
              .getUnitPrice()));
    }
  }

  private void createPartialMeshCells(ResponseConstructionSystemDTO cs, XSSFRow dataRow) {
    ConstructionSystemMaterialDTO constructionSystemPartialMesh = fetchPartialMesh(cs.getMaterials());
    if (Objects.nonNull(constructionSystemPartialMesh)) {
      dataRow
          .createCell(13)
          .setCellValue(constructionSystemPartialMesh
              .getMaterial()
              .getProduct());
      dataRow
          .createCell(14)
          .setCellValue(decimalFormatter(constructionSystemPartialMesh
              .getMaterial()
              .getUnitPrice()));
      dataRow
          .createCell(15)
          .setCellValue(constructionSystemPartialMesh.getCoefficient());

    }
  }


}
