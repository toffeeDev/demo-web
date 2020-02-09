package com.top.demoweb.controller;

import com.top.demoweb.util.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/upload")
@Api(tags = "Upload")
@Slf4j
public class UploadController {

  @Value("${config-path.upload-file.demo-web}")
  private String uploadFilePath;

  @PostMapping("/single-file")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "singleFile Upload", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> singleFileUpload(@RequestParam("file") MultipartFile file) {
    UploadController.log.info("singleFileUpload()");
    if (file.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ResponseUtils.notFound("Please select a file to upload"));
    }

    // ------------------------ creat-Directory-Path -----------------------------
    File creatPath = new File(uploadFilePath); // initial file (folder)
    if (!creatPath.exists()) { // check folder exists
      if (creatPath.mkdirs()) {
        UploadController.log.info("Directory is created!");
      } else {
        UploadController.log.error("Failed to create directory!");
      }
    }
    // ------------------------ creat-File -----------------------------
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    Path path = Paths.get(uploadFilePath);
    try {
      Path targetLocation = path.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return ResponseEntity.status(HttpStatus.OK)
          .body(ResponseUtils.custom(targetLocation, "You successfully uploaded"));
    } catch (IOException ex) {
      return ResponseEntity.status(HttpStatus.OK).body(ResponseUtils.custom(null, fileName));
    }
  }
}
