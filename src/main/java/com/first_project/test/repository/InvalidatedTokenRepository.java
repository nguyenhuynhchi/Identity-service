package com.first_project.test.repository;

import com.first_project.test.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository  extends JpaRepository<InvalidatedToken, String> {

}
