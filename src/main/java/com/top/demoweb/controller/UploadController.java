package com.top.demoweb.controller;

import com.top.demoweb.service.UploadService;
import com.top.demoweb.util.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  private final UploadService uploadService;

  @Autowired
  public UploadController(UploadService uploadService) {
    this.uploadService = uploadService;
  }

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
    Path path = uploadService.singleFileUpload(file);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseUtils.custom(path, "Success upload"));
  }

  @GetMapping("/list-upload-file")
  @ApiOperation(
      value = "listUploadedFiles",
      response = ResponseUtils.class,
      responseContainer = "List")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> listUploadedFiles() {
    UploadController.log.info("listUploadedFiles");
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseUtils.result(uploadService.listUploadedFiles()));
  }

  @GetMapping("/files/{filename:.+}")
  @ApiOperation(
      value = "listUploadedFiles",
      response = ResponseUtils.class,
      responseContainer = "List")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

    Resource file = uploadService.serveFile(filename);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }
}
