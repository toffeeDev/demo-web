package com.top.demoweb.service;

import com.top.demoweb.util.FilesUtils;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Value("${config-path.upload-file.demo-web}")
  private String uploadFilePath;

  public Path singleFileUpload(MultipartFile file) {
    return FilesUtils.uploadFile(file, uploadFilePath);
  }

  public List<Object> listUploadedFiles() {
    return FilesUtils.listFileAll(uploadFilePath)
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toList());
  }

  public Resource serveFile(String filename) {
    return FilesUtils.loadAsResource(filename, uploadFilePath);
  }

  public void deleteFileAll(){
    FilesUtils.deleteAll(uploadFilePath);
  }
}
