package com.optimal.standard.service;

import static com.optimal.standard.util.SheetUtils.createHeaders;
import static com.optimal.standard.util.SheetUtils.fetchBaseMaterial;
import static com.optimal.standard.util.SheetUtils.fetchPartialMesh;
import static com.optimal.standard.util.SheetUtils.fetchTotalMesh;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportService {

  private static String SHEET_NAME = "Report";

  private final ConstructionSystemService constructionSystemService;

  public ByteArrayInputStream generateXlsx() throws IOException {
    List<ResponseConstructionSystemDTO> constructionSystems = this.constructionSystemService.findAll();
    HSSFWorkbook workbook = new HSSFWorkbook();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    HSSFSheet sheet = workbook.createSheet(SHEET_NAME);
    createHeaders(sheet, workbook);

    AtomicInteger dataRowIndex = new AtomicInteger(1);
    for (ResponseConstructionSystemDTO cs : constructionSystems) {
      HSSFRow dataRow = sheet.createRow(dataRowIndex.get());

      HSSFCell cell0 = dataRow.createCell(0);
      cell0.setCellValue(cs.getId());
      cell0
          .getCellStyle()
          .setAlignment(HorizontalAlignment.LEFT);
      dataRow
          .createCell(1)
          .setCellValue(cs
              .getApplicationArea()
              .getName());
      dataRow
          .createCell(2)
          .setCellValue(cs.getTotalPrice());

      ConstructionSystemMaterialDTO constructionSystemBaseMaterial = this.buildBaseMaterial(cs, dataRow);

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

      this.buildTotalMesh(cs, dataRow);
      this.buildPartialMesh(cs, dataRow);

      int indexAfterPlugins = this.buildPlugins(cs, dataRow);
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

  private ConstructionSystemMaterialDTO buildBaseMaterial(ResponseConstructionSystemDTO cs, HSSFRow dataRow) {
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
          .setCellValue(constructionSystemBaseMaterial
              .getMaterial()
              .getUnitPrice());
      dataRow
          .createCell(6)
          .setCellValue(constructionSystemBaseMaterial
              .getMaterial()
              .getComponent());
    }
    return constructionSystemBaseMaterial;
  }

  private void buildTotalMesh(ResponseConstructionSystemDTO cs, HSSFRow dataRow) {
    ConstructionSystemMaterialDTO constructionSystemTotalMesh = fetchTotalMesh(cs.getMaterials());
    if (Objects.nonNull(constructionSystemTotalMesh)) {
      dataRow
          .createCell(11)
          .setCellValue(constructionSystemTotalMesh
              .getMaterial()
              .getProduct());
      dataRow
          .createCell(12)
          .setCellValue(constructionSystemTotalMesh
              .getMaterial()
              .getUnitPrice());
    }
  }

  private void buildPartialMesh(ResponseConstructionSystemDTO cs, HSSFRow dataRow) {
    ConstructionSystemMaterialDTO constructionSystemPartialMesh = fetchPartialMesh(cs.getMaterials());
    if (Objects.nonNull(constructionSystemPartialMesh)) {
      dataRow
          .createCell(13)
          .setCellValue(constructionSystemPartialMesh
              .getMaterial()
              .getProduct());
      dataRow
          .createCell(14)
          .setCellValue(constructionSystemPartialMesh
              .getMaterial()
              .getUnitPrice());
      dataRow
          .createCell(15)
          .setCellValue(constructionSystemPartialMesh.getCoefficient());

    }
  }

  private int buildPlugins(ResponseConstructionSystemDTO cs, HSSFRow dataRow) {
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
            .setCellValue(csm
                .getMaterial()
                .getUnitPrice());
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
    return (dataRowForPluginsIndex.get() == 16) ? 30 : dataRowForPluginsIndex.get();
  }


}
