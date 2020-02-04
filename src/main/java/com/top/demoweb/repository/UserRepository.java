package com.top.demoweb.repository;

import com.top.demoweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> ,UserRepositoryCustom {}
