package com.top.demoweb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class UserRepositoryImpl implements UserRepositoryCustom {
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public UserRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public Long getCountUserName() {
    String sql = " SELECT COUNT(user_name) FROM simpel_dev.user ";
    MapSqlParameterSource paramMap = new MapSqlParameterSource();

    try {
      return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Long.class);
    } catch (EmptyResultDataAccessException e) {
      throw null;
    }
  }
}
