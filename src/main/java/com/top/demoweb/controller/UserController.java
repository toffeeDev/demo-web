package com.top.demoweb.controller;

import com.top.demoweb.dto.UserDto;
import com.top.demoweb.entity.UserEntity;
import com.top.demoweb.service.UserService;
import com.top.demoweb.util.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
@Api(tags = "User")
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
  @ApiOperation(value = "Get UserAll", response = UserDto.class, responseContainer = "List")
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> getUserAll() {
    UserController.log.info("getUserAll");
    return ResponseEntity.status(HttpStatus.OK).body(ResponseUtils.result(userService.userAll()));
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get User By Id", response = UserDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> getUserById(@PathVariable Long id) {
    UserController.log.info("getUserById");
    Optional<UserEntity> user = userService.userById(id);
    if (!user.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.notFound("NO_DATA"));
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(ResponseUtils.result(modelMapper.map(user.get(), UserDto.class)));
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Create User", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseUtils> createUser(@Valid @RequestBody UserDto body) {
    userService.createUser(body);
    UserController.log.info("createUser");
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtils.create());
  }
}
