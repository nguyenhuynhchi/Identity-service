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
import java.util.HashSet;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserService {

  UserRepository userRepository;
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

    HashSet<String> roles = new HashSet<>();
    roles.add(Role.USER.name());
    user.setRoles(roles);
    return userMapper.toUserResponse(userRepository.save(user));
  }

//  public List<User> getUsers() {
//    return userRepository.findAll();
//  }

  // Pre kiểm tra ROLE trước khi thực hiện method
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserResponse> getUsers() {
    log.info("In method get users");
    return userRepository.findAll().stream()
        .map(userMapper::toUserResponse).toList();
  }

  // Post Thực hiện method xong mới kiểm tra ROLE
  @PostAuthorize("returnObject.username == authentication.name")
  public UserResponse getUserID(String userID) {
    log.info("In method get user by ID");
    return userMapper.toUserResponse(userRepository.findById(userID)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
  }

  // Bị lỗi không xác định khi get API với username
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

    // Nếu mật khẩu mới được cung cấp, mã hóa mật khẩu
    if (request.getPassword() != null && !request.getPassword().isEmpty()) {
//      PasswordEncoder passwordEncode = new BCryptPasswordEncoder(10);
      request.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    userMapper.updateUser(user, request);
    // Sử dụng Mapper nên không cần
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());

    return userMapper.toUserResponse(userRepository.save(user));
  }

  public void deleteUserID(String userID) {
    if (!userRepository.existsById(
        userID)) {   // kiểm tra ID có tồn tại không (tồn tại: false; k tồn tại: true)
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
    userRepository.deleteById(userID);
  }

  public void deleteUsername(String username) {
    userRepository.delete(userRepository.findByUsername(username)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
  }
}
