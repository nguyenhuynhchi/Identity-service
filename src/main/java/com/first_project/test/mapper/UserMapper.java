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

  UserResponse  toUserResponse(User user);

  User toUser(UserCreationRequest request);

  @Mapping(target = "roles", ignore = true)
  void updateUser(@MappingTarget User user, UserUpdateRequest request);

//  @Named("mapRolesToString")
//  default Set<String> mapRolesToString(Set<Role> roles) {
//    if (roles == null) return new HashSet<>();
//    return roles.stream().map(Role::getName).collect(Collectors.toSet());
//  }
}
