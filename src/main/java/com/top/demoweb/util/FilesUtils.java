package com.top.demoweb.util;

import com.top.demoweb.exception.FileException;
import com.top.demoweb.exception.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
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

      Path targetLocation = targetLocation(creatDirectoryPath(filePath), filename);

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return targetLocation;
      }
    } catch (IOException e) {
      throw new FileException("Failed to upload file " + filename, e);
    }
  }

  // ------------------------ creat-Directory-Path -----------------------------

  private static Path creatDirectoryPath(String path) {
    try {
      Path pathFile = Paths.get(path);
      // initial file (folder)
      Files.createDirectories(pathFile);
      return pathFile;
    } catch (IOException e) {
      throw new FileException("Failed to create directory!", e);
    }
  }

  //  private static void creatDirectoryPath(String path) {
  //    File folder = new File(path); // initial file (folder)
  //    if (!folder.exists()) { // check folder exists
  //      if (folder.mkdir()) {
  //        FilesUtils.log.info(path + " : Directory is created!");
  //      } else {
  //        FilesUtils.log.error(path + " : Failed to create directory!");
  //      }
  //    }
  //  }

  // ------------------------ list-file-All -----------------------------
  public static Stream<Path> listFileAll(String filePath) {
    Path pathFile = creatDirectoryPath(filePath);
    try {
      return Files.walk(pathFile, 1)
          .filter(path -> !path.equals(pathFile))
          .map(pathFile::relativize);
    } catch (IOException e) {
      throw new FileException("Failed to read stored files", e);
    }
  }

  // ------------------------ targetLocation -----------------------------
  private static Path targetLocation(Path path, String filename) {
    return path.resolve(filename);
  }

  // ------------------------ loadAsResource -----------------------------
  public static Resource loadAsResource(String filename, String filePath) {
    try {
      Resource resource =
          new UrlResource(targetLocation(creatDirectoryPath(filePath), filename).toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new FileNotFoundException("Could not read file: " + filename);
      }
    } catch (MalformedURLException e) {
      throw new FileNotFoundException("Could not read file: " + filename, e);
    }
  }

  // ------------------------ deleteAll -----------------------------
  public static void deleteAll(String filePath) {
    FileSystemUtils.deleteRecursively(creatDirectoryPath(filePath).toFile());
  }
}
