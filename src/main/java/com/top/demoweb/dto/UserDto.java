package com.top.demoweb.dto;

import java.sql.Timestamp;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

  @NotNull
  @Size(min = 5, max = 45, message = "Please provide userName size between 5 - 45")
  private String userName;

  @NotNull
  @Size(min = 6, max = 10, message = "Please provide password size between 6 - 10")
  private String password;

  @NotNull
  @Email(message = "Please provide valid email address")
  private String email;

  private String status;

  private String createBy;

  private Timestamp createDt;

  private String updateBy;

  private Timestamp updateDt;
}
