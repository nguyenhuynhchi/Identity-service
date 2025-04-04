package com.first_project.test.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Dể dàng tạo các Object
@FieldDefaults(level = AccessLevel.PRIVATE) // Mặc định quyền cho các trường(Field) là private
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ApiResponse<T> {
  @Builder.Default
  int code = 1000;
  String message;
  T result;

//  public int getCode() {
//    return code;
//  }
//
//  public void setCode(int code) {
//    this.code = code;
//  }
//
//  public String getMessage() {
//    return message;
//  }
//
//  public void setMessage(String message) {
//    this.message = message;
//  }
//
//  public T getResult() {
//    return result;
//  }
//
//  public void setResult(T result) {
//    this.result = result;
//  }
}
