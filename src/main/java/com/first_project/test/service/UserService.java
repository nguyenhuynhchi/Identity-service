package com.first_project.test.service;

import com.first_project.test.dto.request.UserCreationRequest;
import com.first_project.test.dto.request.UserUpdateRequest;
import com.first_project.test.dto.response.UserResponse;

import com.first_project.test.entity.User;
import com.first_project.test.enums.Role;

import com.first_project.test.exception.AppException;
import com.first_project.test.exception.ErrorCode;

import com.first_project.test.mapper.UserMapper;

import com.first_project.test.repository.UserRepository;
import com.first_project.test.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserService {

  UserRepository userRepository;
  RoleRepository roleRepository;
  UserMapper userMapper;
  PasswordEncoder passwordEncoder;

  public UserResponse createUser(UserCreationRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }
    User user = userMapper.toUser(request); // userMapper sẽ tự set các field
    // Sử dụng Mapper nên không cần
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());
//    PasswordEncoder passwordEncode = new BCryptPasswordEncoder(10);
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // GỐC
//    HashSet<String> roles = new HashSet<>();
//    roles.add(Role.USER.name());

    var roles = roleRepository.findAllById(request.getRoles());
    user.setRoles(new HashSet<>(roles));

//    user.setRoles(roles);

    return userMapper.toUserResponse(userRepository.save(user));
  }

//  public List<User> getUsers() {
//    return userRepository.findAll();
//  }

  // Pre kiểm tra ROLE trước khi thực hiện method lấy tất cả user
  @PreAuthorize("hasRole('ADMIN')")
//  @PreAuthorize("hasAuthority('APPROVE_POST')")
  public List<UserResponse> getUsers() {
    log.info("In method get users");
    return userRepository.findAll().stream()
        .map(userMapper::toUserResponse).toList();
  }

  // Post Thực hiện method để lấy được thông tin của user rồi kiểm tra trong thông tin token đó có đúng là của user đó không
  @PostAuthorize("returnObject.username == authentication.name")
  public UserResponse getUserID(String userID) {
    log.info("In method get user by ID");
    return userMapper.toUserResponse(userRepository.findById(userID) // kiểm tra User có tồn tại không
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
  }

  // GetUser bằng username (Không nên, chỉ để test)
  @PostAuthorize("returnObject.username == authentication.name")
  public UserResponse getUserName(String username) {
    return userMapper.toUserResponse(userRepository.findByUsername(username)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
  }

  public UserResponse getMyInfo(){
    var context = SecurityContextHolder.getContext();
    String name = context.getAuthentication().getName();
    User user = userRepository.findByUsername(name).orElseThrow(
        () -> new AppException(ErrorCode.USER_NOT_EXISTED));
    return userMapper.toUserResponse(user);
  }

  public UserResponse updateUser(String userID, UserUpdateRequest request) {
    User user = userRepository.findById(userID)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    userMapper.updateUser(user, request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    var roles = roleRepository.findAllById(request.getRoles());
    user.setRoles(new HashSet<>(roles));

    return userMapper.toUserResponse(userRepository.save(user));
  }

  public void deleteUserID(String userID) {
    userRepository.findById(userID)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    userRepository.deleteById(userID);
  }

  public void deleteUsername(String username) {
    userRepository.delete(userRepository.findByUsername(username)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
  }
}
