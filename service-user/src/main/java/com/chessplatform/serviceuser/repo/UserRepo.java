package com.chessplatform.serviceuser.repo;

import com.chessplatform.serviceuser.repo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
