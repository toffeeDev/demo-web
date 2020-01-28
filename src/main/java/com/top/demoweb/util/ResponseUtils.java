package com.top.demoweb.util;

import com.top.demoweb.constant.CommonConstants;
import com.top.demoweb.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Saknarin Aranmala (Toffee)
 * @since: Nov 15, 2019
 * @author refer Sompong
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUtils {
  private Integer responseCode;
  private String responseStatus;
  private String responseMessage;
  private Object result;

  public static ResponseUtils result(Object o) {
    return ResponseUtils.builder()
        .result(o)
        .responseCode(200)
        .responseStatus(Status.SUCCESS.getValue())
        .responseMessage("Get Data Success")
        .build();
  }

  public static ResponseUtils custom(Object o, String msg) {
    return ResponseUtils.builder()
        .result(o)
        .responseCode(200)
        .responseStatus(Status.SUCCESS.getValue())
        .responseMessage(msg)
        .build();
  }

  public static ResponseUtils create() {
    return ResponseUtils.builder()
        .responseCode(201)
        .responseStatus(Status.SUCCESS.getValue())
        .responseMessage("Created Success")
        .build();
  }

  public static ResponseUtils update() {
    return ResponseUtils.builder()
        .responseCode(200)
        .responseStatus(Status.SUCCESS.getValue())
        .responseMessage("Updated Success")
        .build();
  }

  public static ResponseUtils delete() {
    return ResponseUtils.builder()
        .responseCode(201)
        .responseStatus(Status.SUCCESS.getValue())
        .responseMessage("Delete Success")
        .build();
  }

  public static ResponseUtils notFound(String msg) {
    return ResponseUtils.builder()
        .responseCode(404)
        .responseStatus(CommonConstants.MASSAGE_ERROR.ERM0001)
        .responseMessage(msg)
        .build();
  }

  public static ResponseUtils conflict(String msg) {
    return ResponseUtils.builder()
        .responseCode(409)
        .responseStatus(CommonConstants.MASSAGE_ERROR.ERM0002)
        .responseMessage(msg)
        .build();
  }
}
