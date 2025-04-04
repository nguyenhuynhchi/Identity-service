package com.first_project.test.mapper;

import com.first_project.test.dto.request.PermissionRequest;
import com.first_project.test.dto.response.PermissionResponse;
import com.first_project.test.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

  Permission toPermission(PermissionRequest request);

  PermissionResponse toPermissionResponse(Permission permission);

}
