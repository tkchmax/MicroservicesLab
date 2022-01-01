package com.chessplatform.serviceuser.repo;

import com.chessplatform.serviceuser.repo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
