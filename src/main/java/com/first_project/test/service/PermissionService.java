package com.first_project.test.service;

import com.first_project.test.dto.request.PermissonRequest;
import com.first_project.test.dto.response.PermissonResponse;
import com.first_project.test.entity.Permission;
import com.first_project.test.mapper.PermissonMapper;
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
  PermissonMapper permissonMapper;

  public PermissonResponse create(PermissonRequest request) {
    Permission permission = permissonMapper.toPermisson(request);
    permission = permissionRepository.save(permission);
    return permissonMapper.toPermissonResponse(permission);
  }

  public List<PermissonResponse> getAll(){
    var permissons = permissionRepository.findAll();
    return permissons.stream().map(permissonMapper::toPermissonResponse).toList();
  }

  public void delete(String permisson){
    permissionRepository.deleteById(permisson);
  }
}
