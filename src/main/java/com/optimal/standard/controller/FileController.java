package com.optimal.standard.controller;

import com.optimal.standard.dto.FilesDTO;
import com.optimal.standard.persistence.model.MaterialFiles;
import com.optimal.standard.service.MaterialFileService;
import com.optimal.standard.service.MaterialService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
public class FileController {

  private MaterialFileService materialFileService;

  private MaterialService materialService;

  @GetMapping(value = "/public/file/load")
  public ResponseEntity<ByteArrayResource> getFile(@RequestParam("file_id") Long fileId) throws IOException {
    MaterialFiles materialFiles = this.materialFileService.getFile(fileId);
    String localFilePath = this.materialFileService.getLocalFilePath(materialFiles);

    final File iFile = new File(localFilePath);
    final long resourceLength = iFile.length();
    final long lastModified = iFile.lastModified();

    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + materialFiles.getName())
        .contentLength(resourceLength)
        .lastModified(lastModified)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new ByteArrayResource(Files.readAllBytes(Path.of(localFilePath))));
  }

  @PostMapping("/file/upload")
  public FilesDTO uploadFile(@RequestParam(value = "material_id", required = false) Long materialId,
      @RequestParam("files") MultipartFile files) {
    return this.materialFileService.saveFiles(this.materialService.findMaterialByIdWithoutException(materialId), files);
  }

  @DeleteMapping("/file/delete")
  public void deleteFile(@RequestBody FilesDTO files) {
    this.materialFileService.deleteFiles(files);
  }

}
