package com.first_project.test.controller;

import com.first_project.test.dto.request.ApiResponse;
import com.first_project.test.dto.request.UserCreationRequest;
import com.first_project.test.dto.request.UserUpdateRequest;
import com.first_project.test.dto.response.UserResponse;
import com.first_project.test.entity.User;
import com.first_project.test.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));

        return apiResponse;
    }

    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/searchByID/{userID}")
    UserResponse getUserID(@PathVariable("userID") String userID){
        return userService.getUserID(userID);
    }

    // Chỉ để test không nên cho phép search username như vậy vì có thể lộ thông tin người dùng
    @GetMapping("/searchByName/{username}")
    UserResponse getUsername(@PathVariable("username") String userName){
        return userService.getUserName(userName);
    }
    // Có thể thay thế (Sử dụng annotation RequestParam, với path: /search?username=...)
//    @GetMapping("/search")
//    UserResponse getUserByUsername(@RequestParam("username") String username) {
//        return userService.getUserByUsername(username);
//    }

    @PutMapping("/{userID}")
    UserResponse updateUser(@PathVariable String userID, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userID, request);
    }

    @DeleteMapping("deleteByID/{userID}")
    String deleteUserID(@PathVariable String userID){

        userService.deleteUserID(userID);
        return "User has been deleted";
    }

    // Chỉ để test không nên cho phép xóa qua username như vậy
    @DeleteMapping("deleteByName/{username}")
    String deleteUsername(@PathVariable String username){
        userService.deleteUsername(username);
        return "User has been deleted";
    }
}
