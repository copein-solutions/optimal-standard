package com.optimal.standard.util;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.ConstructionSystemMaterialDTO;
import com.optimal.standard.dto.ResponseConstructionSystemCommentDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.persistence.model.ApplicationArea;
import com.optimal.standard.persistence.model.ConstructionSystem;
import com.optimal.standard.persistence.model.ConstructionSystemComment;
import com.optimal.standard.persistence.model.ConstructionSystemMaterial;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.optimal.standard.util.MaterialMapperUtils.toMaterialDTO;

public interface ConstructionSystemMapperUtils {

    static ConstructionSystem toConstructionSystem(ConstructionSystemDTO constructionSystem, ApplicationArea applicationArea) {
        return ConstructionSystem
                .builder()
                .id(constructionSystem.getId())
                .totalConsumption(constructionSystem.getTotalConsumption())
                .layers(constructionSystem.getLayers())
                .totalPrice(constructionSystem.getTotalPrice())
                .applicationMode(constructionSystem.getApplicationMode())
                .cured(constructionSystem.isCured())
                .applicationArea(applicationArea)
                .baseConditions(constructionSystem.getBaseConditions())
                .supportConditions(constructionSystem.getSupportConditions())
                .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
                .materialAreaDescription(constructionSystem.getMaterialAreaDescription())
                .build();
    }

    static ConstructionSystemDTO toDTO(ConstructionSystem constructionSystem) {
        return ConstructionSystemDTO
                .builder()
                .id(constructionSystem.getId())
                .totalConsumption(constructionSystem.getTotalConsumption())
                .layers(constructionSystem.getLayers())
                .applicationMode(constructionSystem.getApplicationMode())
                .cured(constructionSystem.isCured())
                .baseConditions(constructionSystem.getBaseConditions())
                .supportConditions(constructionSystem.getSupportConditions())
                .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
                .applicationAreaId(constructionSystem
                        .getApplicationArea()
                        .getId())
                .applicationAreaName(constructionSystem
                        .getApplicationArea()
                        .getName())
                .build();
    }

    static ResponseConstructionSystemDTO toResponseDTO(ConstructionSystem constructionSystem) {
        return ResponseConstructionSystemDTO
                .builder()
                .id(constructionSystem.getId())
                .totalConsumption(constructionSystem.getTotalConsumption())
                .layers(constructionSystem.getLayers())
                .applicationMode(constructionSystem.getApplicationMode())
                .cured(constructionSystem.isCured())
                .totalPrice(constructionSystem.getTotalPrice())
                .baseConditions(constructionSystem.getBaseConditions())
                .supportConditions(constructionSystem.getSupportConditions())
                .materialAreaRestrictions(constructionSystem.getMaterialAreaRestrictions())
                .materialAreaDescription(constructionSystem.getMaterialAreaDescription())
                .applicationArea(ApplicationAreaMapperUtils.toDTO(constructionSystem.getApplicationArea()))
                .materials(toConstructionSystemMaterials(constructionSystem.getConstructionSystemMaterials()))
                .comments(toConstructionSystemComments(constructionSystem.getConstructionSystemComments()))
                .systemCategory(constructionSystem.getSystemCategory())
                .build();
    }

    static List<ConstructionSystemMaterialDTO> toConstructionSystemMaterials(List<ConstructionSystemMaterial> constructionSystemMaterials) {
        return constructionSystemMaterials
                .stream()
                .map(ConstructionSystemMapperUtils::toConstructionSystemMaterial)
                .collect(Collectors.toList());
    }

    static ConstructionSystemMaterialDTO toConstructionSystemMaterial(ConstructionSystemMaterial constructionSystemMaterial) {
        return ConstructionSystemMaterialDTO
                .builder()
                .id(constructionSystemMaterial.getId())
                .typeOfUse(constructionSystemMaterial.getTypeOfUse())
                .material(toMaterialDTO(constructionSystemMaterial.getMaterial()))
                .coefficient(constructionSystemMaterial.getCoefficient())
                .coefficientDescription(constructionSystemMaterial.getCoefficientDescription())
                .materialDescription(constructionSystemMaterial.getMaterialDescription())
                .build();
    }

    static List<ResponseConstructionSystemCommentDTO> toConstructionSystemComments(List<ConstructionSystemComment> constructionSystemComments) {
        return constructionSystemComments
                .stream()
                .sorted(Comparator.comparing(ConstructionSystemComment::getCreatedDate, Comparator.reverseOrder()))
                .limit(5)
                .map(ConstructionSystemMapperUtils::toConstructionSystemComment)
                .collect(Collectors.toList());
    }

    static ResponseConstructionSystemCommentDTO toConstructionSystemComment(ConstructionSystemComment constructionSystemComment) {
        return ResponseConstructionSystemCommentDTO
                .builder()
                .id(constructionSystemComment.getId())
                .comment(constructionSystemComment.getComment())
                .date(constructionSystemComment.getCreatedDate())
                .constructionSystemId(constructionSystemComment.getConstructionSystem().getId())
                .userName(constructionSystemComment.getRegisteredUser().getUsername())
                .userCompleteName(constructionSystemComment.getRegisteredUser().getName())
                .build();
    }

    static ResponseConstructionSystemDTO toResponseCommentsDTO(ConstructionSystem constructionSystem) {
        return ResponseConstructionSystemDTO
                .builder()
                .comments(toConstructionSystemAllComments(constructionSystem.getConstructionSystemComments()))
                .build();
    }

    static List<ResponseConstructionSystemCommentDTO> toConstructionSystemAllComments(List<ConstructionSystemComment> constructionSystemComments) {
        return constructionSystemComments
                .stream()
                .sorted(Comparator.comparing(ConstructionSystemComment::getCreatedDate, Comparator.reverseOrder()))
                .map(ConstructionSystemMapperUtils::toConstructionSystemComment)
                .collect(Collectors.toList());
    }

    public static byte[] exportToXls(List<ConstructionSystem> list) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    private static JasperPrint getReport(List<ConstructionSystem> list) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("systemsData", new JRBeanCollectionDataSource(list));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:optimalStandard_Report.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());

        return report;
    }
}
