package com.top.demoweb.controller;

import com.top.demoweb.service.FileService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/file")
@Api(tags = "File")
@Slf4j
public class FileController {

  private final FileService fileService;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping("upload/single-file")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Upload Single File", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> uploadSingleFile(@RequestParam("file") MultipartFile file) {
    FileController.log.info("uploadSingleFile()");
    Path path = fileService.singleFileUpload(file);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseUtils.custom(path, "Success upload"));
  }

  @GetMapping("/list")
  @ApiOperation(value = "List Files", response = ResponseUtils.class, responseContainer = "List")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> listFiles() {
    FileController.log.info("list");
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseUtils.result(fileService.listUploadedFiles()));
  }

  @GetMapping("/download/{filename:.+}")
  @ApiOperation(
      value = "Download Files",
      response = ResponseUtils.class,
      responseContainer = "List")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
    FileController.log.info("downloadFile");
    Resource file = fileService.serveFile(filename);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }

  @DeleteMapping("/delete/all")
  @ApiOperation(value = "Delete Files ALL", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> deleteFilesAll() {
    fileService.deleteFileAll();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseUtils.delete());
  }

  @DeleteMapping("/delete/{filename:.+}")
  @ApiOperation(value = "Delete Files", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> deleteFiles(@PathVariable String filename) {
    fileService.deleteFileByFileName(filename);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseUtils.delete());
  }
}
