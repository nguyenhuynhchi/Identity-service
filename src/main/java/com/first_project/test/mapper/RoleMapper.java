package com.first_project.test.mapper;


import com.first_project.test.dto.request.RoleRequest;
import com.first_project.test.dto.response.RoleResponse;
import com.first_project.test.entity.Role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  @Mapping(target = "permissions", ignore = true)
  Role toRole(RoleRequest request);

  RoleResponse toRoleResponse(Role role);
}
