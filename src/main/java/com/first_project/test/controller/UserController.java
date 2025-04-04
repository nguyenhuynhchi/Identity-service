package com.first_project.test.controller;

import com.first_project.test.dto.request.ApiResponse;
import com.first_project.test.dto.request.UserCreationRequest;
import com.first_project.test.dto.request.UserUpdateRequest;
import com.first_project.test.dto.response.UserResponse;
import com.first_project.test.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  @PostMapping
  ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
    return ApiResponse.<UserResponse>builder()
        .result(userService.createUser(request))
        .build();
  }

  @GetMapping
  ApiResponse<List<UserResponse>> getUsers(){
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    log.info("Usename: {}", authentication.getName());
    authentication.getAuthorities()
        .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

    return ApiResponse.<List<UserResponse>>builder()
        .result(userService.getUsers())
        .build();
  }

  @GetMapping("/searchByID/{userID}")
  ApiResponse<UserResponse> getUserID(@PathVariable("userID") String userID) {
    return ApiResponse.<UserResponse>builder()
        .result(userService.getUserID(userID))
        .build();
  }

  // Chỉ để test không nên cho phép search username như vậy vì có thể lộ thông tin người dùng
  @GetMapping("/searchByName/{username}")
  ApiResponse<UserResponse> getUsername(@PathVariable("username") String userName) {
    return ApiResponse.<UserResponse>builder()
        .result(userService.getUserName(userName))
        .build();
  }

  @GetMapping("/myInfo")
  ApiResponse<UserResponse> getMyInfo() {
    return ApiResponse.<UserResponse>builder()
        .result(userService.getMyInfo())
        .build();
  }
  // Có thể thay thế (Sử dụng annotation RequestParam, với path: /search?username=...)
//    @GetMapping("/search")
//    UserResponse getUserByUsername(@RequestParam("username") String username) {
//        return userService.getUserByUsername(username);
//    }

  @PutMapping("/{userID}")
  ApiResponse<UserResponse> updateUser(@PathVariable String userID, @RequestBody UserUpdateRequest request) {
    return ApiResponse.<UserResponse>builder()
        .result(userService.updateUser(userID, request))
        .build();
  }

  @DeleteMapping("deleteByID/{userID}")
  ApiResponse<String> deleteUserID(@PathVariable String userID) {

    userService.deleteUserID(userID);
    return ApiResponse.<String>builder()
        .result("User has been deleted")
        .build();
  }

  // Chỉ để test không nên cho phép xóa qua username như vậy
  @DeleteMapping("deleteByName/{username}")
  ApiResponse<String> deleteUsername(@PathVariable String username) {
    userService.deleteUsername(username);
    return ApiResponse.<String>builder()
        .result("User has been deleted")
        .build();
  }
}
