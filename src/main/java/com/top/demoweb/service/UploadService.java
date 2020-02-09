package com.top.demoweb.service;

import com.top.demoweb.util.FilesUtils;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

  @Value("${config-path.upload-file.demo-web}")
  private String uploadFilePath;

  public Path singleFileUpload( MultipartFile file) {
    return FilesUtils.uploadFile(file,uploadFilePath);
  }
}
