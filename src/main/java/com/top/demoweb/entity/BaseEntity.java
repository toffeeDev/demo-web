package com.top.demoweb.entity;

import com.top.demoweb.constant.Status;
import com.top.demoweb.util.UserLoginUtils;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@MappedSuperclass
public abstract class BaseEntity {
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

  @PrePersist
  public void prePersist() {
    status = Status.ACTIVE.getValue();
    if (StringUtils.isBlank(createBy)) {
      createBy = UserLoginUtils.getCurrentUsername();
    }
    createDt = new Timestamp(System.currentTimeMillis());
  }

  @PreUpdate
  public void preUpdate() {
    if (StringUtils.isBlank(updateBy)) {
      updateBy = UserLoginUtils.getCurrentUsername();
    }
    updateDt = new Timestamp(System.currentTimeMillis());
  }
}
