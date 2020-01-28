package com.top.demoweb.constant;

public enum Status {
  ACTIVE("A"),
  INACTIVE("I"),
  ISDELETE("D"),
  SUCCESS("Success"),
  FAIL("Failure");

  private String value;

  Status(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
