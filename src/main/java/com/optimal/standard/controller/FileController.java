package com.optimal.standard.controller;

import static com.optimal.standard.service.files.LocalFilesService.DIRECTORY_SEPARATOR;
import static com.optimal.standard.service.files.LocalFilesService.LOCAL_DIRECTORY_UPLOADS;

import com.optimal.standard.dto.FilesDTO;
import com.optimal.standard.persistence.model.MaterialFiles;
import com.optimal.standard.service.MaterialFileService;
import com.optimal.standard.service.MaterialService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

  private MaterialFileService materialFileService;

  private MaterialService materialService;

  @GetMapping(value = "/load", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<ByteArrayResource> getFile(@RequestParam("file_id") Long fileId) throws IOException {
    MaterialFiles materialFiles = this.materialFileService.getFile(fileId);
    Path uploadsDirectory = Paths
        .get(LOCAL_DIRECTORY_UPLOADS)
        .toAbsolutePath();
    String localFinalPath = uploadsDirectory + DIRECTORY_SEPARATOR + materialFiles.getName();

    final File iFile = new File(localFinalPath);
    final long resourceLength = iFile.length();
    final long lastModified = iFile.lastModified();

    return ResponseEntity
        .ok()
        .header("Content-Disposition", "inline; filename=" + materialFiles.getName())
        .contentLength(resourceLength)
        .lastModified(lastModified)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new ByteArrayResource(Files.readAllBytes(Path.of(localFinalPath))));
  }

  @PostMapping("/upload")
  public FilesDTO uploadFile(@RequestParam(value = "material_id", required = false) Long materialId,
      @RequestParam("files") MultipartFile files) {
    return this.materialFileService.saveFiles(this.materialService.findMaterialByIdWithoutException(materialId), files);
  }

  @DeleteMapping("/delete")
  public void deleteFile(@RequestBody FilesDTO files) {
    this.materialFileService.deleteFiles(files);
  }

}
