package com.top.demoweb.entity;

import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "simpel_dev")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Basic
  @Column(name = "user_name", length = 45)
  private String userName;

  @Basic
  @Column(name = "password", length = 45)
  private String password;

  @Basic
  @Column(name = "email", length = 45)
  private String email;

  @Basic
  @Column(name = "status", nullable = false, length = 1)
  private String status;

  @Basic
  @Column(name = "create_by", length = 45)
  private String createBy;

  @Basic
  @Column(name = "create_dt", nullable = false)
  private Timestamp createDt;

  @Basic
  @Column(name = "update_by", length = 45)
  private String updateBy;

  @Basic
  @Column(name = "update_dt", nullable = false)
  private Timestamp updateDt;
}
