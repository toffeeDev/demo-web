package com.top.demoweb.service;

import com.top.demoweb.dto.UserDto;
import com.top.demoweb.entity.UserEntity;
import com.top.demoweb.repository.UserRepository;
import com.top.demoweb.util.ObjectMapperUtils;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

  private final ModelMapper modelMapper;
  private final UserRepository userRepository;

  @Autowired
  public UserService(ModelMapper modelMapper, UserRepository userRepository) {
    this.modelMapper = modelMapper;
    this.userRepository = userRepository;
  }

  public List<UserDto> userAll() {
    List<UserEntity> listUserEntity = userRepository.findAll();
    return ObjectMapperUtils.mapAll(listUserEntity, UserDto.class);
  }

  public Optional<UserEntity> userById(Long id) {
    return userRepository.findById(id);
  }

  public Long countUserName() {
    return userRepository.getCountUserName();
  }

  @Transactional(rollbackOn = {Exception.class})
  public void createUser(UserDto userDto) {
    userDto.setId(null);
    userRepository.save(modelMapper.map(userDto, UserEntity.class));
  }


}
