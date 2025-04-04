package com.first_project.test.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Dể dàng tạo các Object
@FieldDefaults(level = AccessLevel.PRIVATE) // Mặc định quyền cho các trường(Field) là private

public class UserUpdateRequest {

  @Size(min = 3, max = 50, message = "USERNAME_INVALID")
  @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "NAME_INVALID")
  String username;
  @Size(min = 8, message = "USERPASSWORD_INVALID")
  String password;
  @Pattern(regexp = "^[a-zA-Z]*$", message = "NAME_INVALID")
  String firstName;
  @Pattern(regexp = "^[a-zA-Z]*$", message = "NAME_INVALID")
  String lastName;
  LocalDate dob;

  List<String> roles;

//  public String getUsername() {
//    return username;
//  }
//
//  public void setUsername(String username) {
//    this.username = username;
//  }
//
//  public String getPassword() {
//    return password;
//  }
//
//  public void setPassword(String password) {
//    this.password = password;
//  }
//
//  public String getFirstName() {
//    return firstName;
//  }
//
//  public void setFirstName(String firstName) {
//    this.firstName = firstName;
//  }
//
//  public String getLastName() {
//    return lastName;
//  }
//
//  public void setLastName(String lastName) {
//    this.lastName = lastName;
//  }
//
//  public LocalDate getDob() {
//    return dob;
//  }
//
//  public void setDob(LocalDate dob) {
//    this.dob = dob;
//  }
}
