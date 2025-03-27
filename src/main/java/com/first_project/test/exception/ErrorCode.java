package com.first_project.test.exception;

public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"), // Lỗi chưa đươc phân loại
  INVALID_KEY(-1, "Uncategorized exception"), // Lỗi do viết enumKey sai chổ nào đó (vẫn hiển thị "Uncategorized exception")
  USER_EXISTED(1001, "User existed (Người dùng đã tồn tại)"),
  USERNAME_INVALID(1002, "Tên sử dụng phải có tối thiểu 3 kí tự"),
  USERPASSWORD_INVALID(1003, "Mật khẩu phải có tối thiểu 8 kí tự"),
  USER_NOT_EXISTED(1004, "User is not existed (Không tìm thấy người dùng này)"),
  NAME_INVALID(1005, "The field must only contain alphabetic characters.(Tên chỉ chứa các kí tự alphabetic)"),
  UNAUTHENTICATED(1006, "Unauthenticated")
  ;

  private int code;
  private String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
