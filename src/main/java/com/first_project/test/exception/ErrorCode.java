package com.first_project.test.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR), // Lỗi chưa đươc phân loại
  INVALID_KEY(-1, "Uncategorized exception", HttpStatus.BAD_REQUEST), // Lỗi do viết enumKey sai chổ nào đó (vẫn hiển thị "Uncategorized exception")
  USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),  // Người dùng đã tồn tại

  // Invalid
  USERNAME_INVALID(1002, "Tên sử dụng phải có tối thiểu {min} kí tự", HttpStatus.BAD_REQUEST),  // {min} là giá trị min trong annotation invalid
  USERPASSWORD_INVALID(1003, "Mật khẩu phải có tối thiểu {min} kí tự", HttpStatus.BAD_REQUEST),
  NAME_INVALID(1005, "The field must only contain alphabetic characters.(Tên chỉ chứa các kí tự alphabetic)", HttpStatus.BAD_REQUEST),

  USER_NOT_EXISTED(1004, "User is not existed (Không tìm thấy người dùng này)", HttpStatus.NOT_FOUND),

  UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
  UNAUTHORIZED(1007, "You do not have permision", HttpStatus.FORBIDDEN),
  INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST)  // Tuoi phair lowns honw 18
  ;

  ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }

  private int code;
  private String message;
  private HttpStatusCode statusCode;

}
