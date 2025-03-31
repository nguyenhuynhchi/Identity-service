package com.first_project.test.dto.response;

import java.time.LocalDate;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


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
  Set<String> roles;
}
