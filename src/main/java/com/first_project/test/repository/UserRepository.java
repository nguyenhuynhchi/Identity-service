package com.first_project.test.repository;

import com.first_project.test.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //Kiểm tra sự tồn tại của userName trong database
    boolean existsByUsername(String username);

//    boolean existsById(String id);
    Optional<User> findByUsername(String username);
}
