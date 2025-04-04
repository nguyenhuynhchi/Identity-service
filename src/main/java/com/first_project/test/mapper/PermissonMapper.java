package com.first_project.test.mapper;

import com.first_project.test.dto.request.PermissonRequest;
import com.first_project.test.dto.response.PermissonResponse;
import com.first_project.test.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissonMapper {

  Permission toPermisson(PermissonRequest request);

  PermissonResponse toPermissonResponse(Permission permission);

}
