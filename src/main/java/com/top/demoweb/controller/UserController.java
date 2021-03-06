package com.top.demoweb.controller;

import com.top.demoweb.dto.UserDto;
import com.top.demoweb.entity.UserEntity;
import com.top.demoweb.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
@Slf4j
public class UserController {
  private final ModelMapper modelMapper;
  private final UserService userService;

  @Autowired
  public UserController(ModelMapper modelMapper, UserService userService) {
    this.modelMapper = modelMapper;
    this.userService = userService;
  }

  @GetMapping("/all")
  public List<UserDto> getUserAll() {
    UserController.log.info("getUserAll");
    return userService.userAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    UserController.log.info("getUserById");
    Optional<UserEntity> user = userService.userById(id);
    if (!user.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(modelMapper.map(user.get(), UserDto.class));
  }
}
