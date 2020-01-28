package com.top.demoweb.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private Long id;

  private String userName;

  private String password;

  private String email;

  private String status;

  private String createBy;

  private Timestamp createDt;

  private String updateBy;

  private Timestamp updateDt;

}
