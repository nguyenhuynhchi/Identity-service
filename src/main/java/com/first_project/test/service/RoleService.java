package com.first_project.test.service;

import com.first_project.test.dto.request.RoleRequest;
import com.first_project.test.dto.response.RoleResponse;
import com.first_project.test.mapper.RoleMapper;
import com.first_project.test.repository.PermissionRepository;
import com.first_project.test.repository.RoleRepository;

import java.util.HashSet;
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
public class RoleService {
  RoleRepository roleRepository;
  PermissionRepository permissionRepository;
  RoleMapper roleMapper;

  public RoleResponse create(RoleRequest request){
    var role = roleMapper.toRole(request);

    var permissions = permissionRepository.findAllById(request.getPermissons());
    role.setPermissions(new HashSet<>(permissions));

    role = roleRepository.save(role);
    return roleMapper.toRoleResponse(role);
  }

  public List<RoleResponse> getAll(){
    return roleRepository.findAll()
        .stream()
        .map(roleMapper::toRoleResponse)
        .toList();
  }

  public void delete(String role){
    roleRepository.deleteById(role);
  }
}
