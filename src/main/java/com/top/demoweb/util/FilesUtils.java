package com.top.demoweb.util;

import com.top.demoweb.exception.FileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Saknarin Aranmala (Toffee)
 * @since: Feb 09, 2020
 */
@Slf4j
public class FilesUtils {
  private FilesUtils() {
    throw new IllegalStateException("Utility class");
  }
  // ------------------------ uploadFile-----------------------------
  public static Path uploadFile(MultipartFile file, String filePath) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      if (file.isEmpty()) {
        throw new FileException("Failed to upload empty file " + filename);
      }
      if (filename.contains("..")) {
        // This is a security check
        throw new FileException(
            "Cannot upload file with relative path outside current directory " + filename);
      }

      creatDirectoryPath(filePath);

      Path pathFile = Paths.get(filePath);
      Path targetLocation = pathFile.resolve(filename);

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return targetLocation;
      }
    } catch (IOException e) {
      throw new FileException("Failed to upload file " + filename, e);
    }
  }

  // ------------------------ creat-Directory-Path -----------------------------
  private static void creatDirectoryPath(String path) {
    File folder = new File(path); // initial file (folder)
    if (!folder.exists()) { // check folder exists
      if (folder.mkdir()) {
        FilesUtils.log.info(path + " : Directory is created!");
      } else {
        FilesUtils.log.error(path + " : Failed to create directory!");
      }
    }
  }

  // ------------------------ load-file-All -----------------------------
  public static Stream<Path> loadFileAll(String filePath) {
    creatDirectoryPath(filePath);

    Path pathFile = Paths.get(filePath);
    try {
      return Files.walk(pathFile, 1)
          .filter(path -> !path.equals(pathFile))
          .map(pathFile::relativize);
    } catch (IOException e) {
      throw new FileException("Failed to read stored files", e);
    }
  }
}
