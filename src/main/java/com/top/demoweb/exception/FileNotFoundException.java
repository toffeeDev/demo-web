package com.top.demoweb.exception;

public class FileNotFoundException extends FileException {
  public FileNotFoundException(String message) {
    super(message);
  }

  public FileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
