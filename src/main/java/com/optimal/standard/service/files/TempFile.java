package com.optimal.standard.service.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Data;

/**
 * Temporary file that can be automatically deleted after its usage in a try-with-resources block or when application is terminated.
 */
@Data
public class TempFile implements AutoCloseable {

  private final File file;

  private boolean automaticDelete;

  public TempFile(File file) {
    this(file, true);
  }

  public TempFile(File file, boolean automaticDelete) {
    this.file = file;
    this.automaticDelete = automaticDelete;
    this.file.deleteOnExit();
  }

  public String getAbsolutePath() {
    return this.file.getAbsolutePath();
  }

  public long getLength() {
    return this.file.length();
  }

  public boolean isEmpty() {
    return this.file.length() == 0;
  }

  public Path getPath() {
    return Paths.get(this.file.getAbsolutePath());
  }

  public byte[] getAllBytes() throws IOException {
    return Files.readAllBytes(this.getPath());
  }

  public InputStream getInputStream() throws IOException {
    return Files.newInputStream(this.getPath());
  }

  @Override
  public void close() {
    if (this.automaticDelete && this.file.exists()) {
      this.delete();
    }
  }

  public boolean delete() {
    return this.file.delete();
  }

}
