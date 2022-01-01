package com.chessplatform.serviceuser.service;

import com.chessplatform.serviceuser.repo.model.Role;
import com.chessplatform.serviceuser.repo.model.User;

import java.util.List;

public interface IUserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    Role getRole(String name);
    long createNewUser(String username, String password, int eloRating) throws Exception;
    void deleteUserByUsername(String username) throws Exception;

    List<User> getUsers();
}
