package com.first_project.test.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class InvalidatedToken {

  @Id
  String id;
  Date expiryTime;
}
