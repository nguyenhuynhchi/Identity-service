package com.first_project.test.service;

import com.first_project.test.dto.request.PermissionRequest;
import com.first_project.test.dto.response.PermissionResponse;
import com.first_project.test.entity.Permission;
import com.first_project.test.mapper.PermissionMapper;
import com.first_project.test.repository.PermissionRepository;

import java.util.List;
import lombok.AccessLevel;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

  PermissionRepository permissionRepository;
  PermissionMapper PermissionMapper;

  public PermissionResponse create(PermissionRequest request) {
    Permission permission = PermissionMapper.toPermission(request);
    permission = permissionRepository.save(permission);
    return PermissionMapper.toPermissionResponse(permission);
  }

  public List<PermissionResponse> getAll(){
    var permissions = permissionRepository.findAll();
    return permissions.stream().map(PermissionMapper::toPermissionResponse).toList();
  }

  public void delete(String Permission){
    permissionRepository.deleteById(Permission);
  }
}
