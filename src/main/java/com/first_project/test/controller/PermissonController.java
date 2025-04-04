package com.first_project.test.controller;


import com.first_project.test.dto.request.ApiResponse;
import com.first_project.test.dto.request.PermissonRequest;
import com.first_project.test.dto.response.PermissonResponse;
import com.first_project.test.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/permissons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissonController {
  PermissionService permissionService;

  @PostMapping
  ApiResponse<PermissonResponse> create(@RequestBody PermissonRequest request){
    return ApiResponse.<PermissonResponse>builder()
        .result(permissionService.create(request))
        .build();
  }

  @GetMapping
  ApiResponse<List<PermissonResponse>> getAll(){
    return ApiResponse.<List<PermissonResponse>>builder()
        .result(permissionService.getAll())
        .build();
  }

  @DeleteMapping("/{permisson}")
  ApiResponse<Void> delete(@PathVariable String permisson){
    permissionService.delete(permisson);
    return ApiResponse.<Void>builder().build();
  }

}
