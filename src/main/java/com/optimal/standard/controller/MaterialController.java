package com.optimal.standard.controller;

import static com.optimal.standard.service.files.LocalFilesService.DIRECTORY_SEPARATOR;
import static com.optimal.standard.service.files.LocalFilesService.LOCAL_DIRECTORY_UPLOADS;

import com.optimal.standard.dto.MaterialDTO;
import com.optimal.standard.persistence.model.MaterialFiles;
import com.optimal.standard.service.MaterialService;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/material")
@Slf4j
public class MaterialController {

  private final MaterialService materialService;

  @GetMapping(value = "/load/files", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<ByteArrayResource> getFile(@RequestParam("file_id") Long fileId) throws IOException, URISyntaxException {
    MaterialFiles materialFiles = this.materialService.getFile(fileId);
    Path uploadsDirectory = Paths
        .get(LOCAL_DIRECTORY_UPLOADS)
        .toAbsolutePath();
    String localFinalPath = uploadsDirectory + DIRECTORY_SEPARATOR + materialFiles.getName();

    final File iFile = new File(localFinalPath);
    final long resourceLength = iFile.length();
    final long lastModified = iFile.lastModified();
//    final InputStream resource = new FileInputStream(iFile);

    return ResponseEntity
        .ok()
        .header("Content-Disposition", "inline; filename=" + materialFiles.getName())
        .contentLength(resourceLength)
        .lastModified(lastModified)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new ByteArrayResource(Files.readAllBytes(Path.of(localFinalPath))));
  }

  @GetMapping()
  public ResponseEntity<List<MaterialDTO>> findAll() {
    return ResponseEntity
        .ok()
        .body(this.materialService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MaterialDTO> findById(@PathVariable Long id) {
    return ResponseEntity
        .ok()
        .body(this.materialService.findById(id));
  }

  @GetMapping("/search")
  public ResponseEntity<List<MaterialDTO>> findAllByType(@RequestParam(value = "type") String type) {
    return ResponseEntity
        .ok()
        .body(this.materialService.findAllByType(type));
  }

  @PostMapping()
  public void create(@RequestBody @Valid MaterialDTO request) {
    this.materialService.saveMaterial(request);
  }

  @PutMapping("/{id}")
  public void update(@PathVariable Long id, @RequestBody @Valid MaterialDTO request) {
    this.materialService.updateMaterial(id, request);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    this.materialService.deleteMaterial(id);
  }

  @PostMapping("/upload/files")
  public void uploadFile(@RequestParam("material_id") Long materialId, @RequestParam("files") MultipartFile files) {
    this.materialService.saveFiles(materialId, files);
  }

  @DeleteMapping("/delete/files")
  public void deleteFile(@RequestParam("material_id") Long materialId) {
    this.materialService.deleteFiles(materialId);
  }

  @GetMapping("/local")
  public List<String> listLocalFiles(@RequestParam(value = "directory", required = false) String localPath) {
    return this.materialService.listTempFiles(localPath);
  }

  @GetMapping("/local/stream")
  public Stream<String> streamLocalFiles(@RequestParam(value = "directory", required = false) String localPath) {
    return this.materialService.getLocalFileLines(localPath);
  }


}
