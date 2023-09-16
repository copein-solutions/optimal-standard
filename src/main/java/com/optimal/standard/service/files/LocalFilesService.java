package com.optimal.standard.service.files;

import static com.optimal.standard.util.TextUtils.sanitizeFileName;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalFilesService {

  public static final String DIRECTORY_SEPARATOR = "/";

  public static final String LOCAL_DIRECTORY_UPLOADS = "uploads";

  private static final String ERROR_WRITING_LOCAL_FILE = "Error writing local file: ";


  private static final String ERROR_MOVING_LOCAL_FILE = "Error moving temp file: ";

  private static final String LOCAL_DIRECTORY_INBOUND = "inbound";

  public static Path DIRECTORY_UPLOADS = Paths
      .get(LOCAL_DIRECTORY_UPLOADS)
      .toAbsolutePath();

  public static Path DIRECTORY_TEMP = Paths
      .get(LOCAL_DIRECTORY_INBOUND)
      .toAbsolutePath();

  private static String getNonDuplicateFileName(String directoryPath, String originalFileName) {
    File directory = new File(directoryPath);
    String fileName = originalFileName;
    int counter = 1;

    while (new File(directory, fileName).exists()) {
      int dotIndex = originalFileName.lastIndexOf(".");
      String nameWithoutExtension = dotIndex != -1 ? originalFileName.substring(0, dotIndex) : originalFileName;
      String extension = dotIndex != -1 ? originalFileName.substring(dotIndex) : "";

      fileName = nameWithoutExtension + "_" + counter + extension;
      counter++;
    }

    return fileName;
  }

  public static String getFinalName(String originalName) {
    return getNonDuplicateFileName(DIRECTORY_TEMP + DIRECTORY_SEPARATOR, sanitizeFileName(requireNonNull(originalName)));
  }

  public String assembleFilePath(String... params) {
    return String.join(DIRECTORY_SEPARATOR, params);
  }

  public List<String> listTempFiles(String localPath) {
    Path path = Paths.get(new File(StringUtils.isEmpty(localPath) ? LOCAL_DIRECTORY_INBOUND : localPath).getAbsolutePath());
    try (Stream<Path> files = Files.list(path)) {
      return files
          .map(Object::toString)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException("Error listing local files: " + localPath, e);
    }
  }

  public Stream<String> getLocalFileLines(TempFile tempFile) {
    try {
      return Files.lines(Paths.get(tempFile.getAbsolutePath()));
    } catch (IOException e) {
      throw new RuntimeException(String.format("Error reading local file: %s", tempFile.getAbsolutePath()), e);
    }
  }

  public void saveFile(MultipartFile multipartFile) {
    final String finalFileName = getFinalName(multipartFile.getOriginalFilename());
    String localTempPath = DIRECTORY_TEMP + DIRECTORY_SEPARATOR + finalFileName;
    TempFile file = new TempFile(new File(localTempPath));
    this.writeToFile(file, multipartFile);
  }

  private void writeToFile(TempFile localTempFile, MultipartFile multipartFile) {
    try (OutputStream fop = Files.newOutputStream(localTempFile.getPath()); InputStream is = multipartFile.getInputStream()) {
      is.transferTo(fop);
    } catch (IOException e) {
      localTempFile.close();
      throw new RuntimeException(ERROR_WRITING_LOCAL_FILE + localTempFile.getAbsolutePath(), e);
    }
  }

  public void moveTempFile(String filename) {
    String localTempPath = DIRECTORY_TEMP + DIRECTORY_SEPARATOR + filename;
    String localFinalPath = DIRECTORY_UPLOADS + DIRECTORY_SEPARATOR + filename;
    try {
      OutputStream fop = Files.newOutputStream(Path.of(localFinalPath));
      FileInputStream input = new FileInputStream(localTempPath);
      input.transferTo(fop);
    } catch (IOException e) {
      throw new RuntimeException(ERROR_MOVING_LOCAL_FILE + localTempPath, e);
    }
  }

  public String deleteFile(String fileName) {
    try {
      Path filePath = Paths.get(new File(DIRECTORY_UPLOADS + DIRECTORY_SEPARATOR + fileName).getAbsolutePath());
      Files.delete(filePath);
      return filePath.toString();
    } catch (IOException e) {
      throw new RuntimeException("Error deleting local file: " + fileName, e);
    }
  }

}
