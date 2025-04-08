package com.first_project.test.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Dể dàng tạo các Object
@FieldDefaults(level = AccessLevel.PRIVATE) // Mặc định quyền cho các trường(Field) là private
public class UserResponse {
  String id;
  String username;
//  String password; Không trả về password
  String firstName;
  String lastName;
  LocalDate dob;
//  Set<String> roles;  // GỐC
  Set<RoleResponse> roles;
}
