package com.first_project.test.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Dể dàng tạo các Object
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
  String name;
  String description;
  Set<String> permissions;
}
