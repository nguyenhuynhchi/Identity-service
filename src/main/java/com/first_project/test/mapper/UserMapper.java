package com.first_project.test.mapper;

import com.first_project.test.dto.request.UserCreationRequest;
import com.first_project.test.dto.request.UserUpdateRequest;
import com.first_project.test.dto.response.UserResponse;
import com.first_project.test.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User toUser(UserCreationRequest request);

  UserResponse toUserResponse(User user);

  void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
