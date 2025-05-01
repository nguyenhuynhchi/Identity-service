package com.first_project.test.repository;

import com.first_project.test.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

//    Optional<Role> findByName(String name);
}
