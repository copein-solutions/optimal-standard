package com.optimal.standard.service.files;

import java.io.File;
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

  private static final String LOCAL_DIRECTORY_INBOUND = "inbound";

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

  public void saveToTempFile(MultipartFile multipartFile) {
    Path tempDirectory = Paths
        .get(LOCAL_DIRECTORY_INBOUND)
        .toAbsolutePath();
    String localTempPath = tempDirectory + DIRECTORY_SEPARATOR + multipartFile.getOriginalFilename();

    TempFile file = new TempFile(new File(localTempPath));

    this.writeToFile(file, multipartFile);
  }

  public String deleteTempFile(String path) {
    try {
      Path filePath = Paths.get(new File(path).getAbsolutePath());
      Files.delete(filePath);
      return filePath.toString();
    } catch (IOException e) {
      throw new RuntimeException("Error deleting local file: " + path, e);
    }
  }

  private void writeToFile(TempFile localTempFile, MultipartFile multipartFile) {
    try (OutputStream fop = Files.newOutputStream(localTempFile.getPath()); InputStream is = multipartFile.getInputStream()) {
      is.transferTo(fop);
      this.moveTempFileToPermanentDirectory(multipartFile);
    } catch (IOException e) {
      localTempFile.close();
      throw new RuntimeException(ERROR_WRITING_LOCAL_FILE + localTempFile.getAbsolutePath(), e);
    }
  }

  private void moveTempFileToPermanentDirectory(MultipartFile multipartFile) throws IOException {
    Path uploadsDirectory = Paths
        .get(LOCAL_DIRECTORY_UPLOADS)
        .toAbsolutePath();
    String localFinalPath = uploadsDirectory + DIRECTORY_SEPARATOR + multipartFile.getOriginalFilename();
    multipartFile.transferTo(new File(localFinalPath));
  }

}
