package com.chessplatform.serviceuser.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.chessplatform.serviceuser.repo.RoleRepo;
import com.chessplatform.serviceuser.repo.UserRepo;
import com.chessplatform.serviceuser.repo.model.Role;
import com.chessplatform.serviceuser.repo.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService, UserDetailsService{

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public com.chessplatform.serviceuser.api.dto.User decodeToken(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        String role = decodedJWT.getClaim("role").asString();
        return new com.chessplatform.serviceuser.api.dto.User(username, role);
    }

    @Override
    public long createNewUser(String username, String password, int eloRating) throws Exception
    {
        User tmpUser = userRepo.findByUsername(username);
        if(tmpUser != null)
            throw new Exception("user with this name is already exist!");

        Role role = roleRepo.findByName("ROLE_PLAYER");
        User user = new User(null, username, password, eloRating, role);
        saveUser(user);
        return user.getId();
    }

    @Override
    public void deleteUserByUsername(String username) throws Exception {
        User user = getUser(username);
        if(user == null) {
            throw new Exception("user does not exist!");
        }
        userRepo.delete(getUser(username));
    }

    @Override
    public User saveUser(User user) {
        userRepo.save(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }

    @Override
    public Role saveRole(Role role) {
        roleRepo.save(role);
        return role;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.setRole(role);
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Role getRole(String name)
    {
        return roleRepo.findByName(name);
    }
    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null)
        {
            log.error("User not found in the db");
            throw new UsernameNotFoundException("User not found in the db");
        }
        else {
            log.info("User {} found in the db", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public void createAdmin()
    {
        saveUser(new User(null, "admin", "admin", 0, getRole("ROLE_ADMIN")));
        saveUser(new User(null, "user1", "pass1", 1925, getRole("ROLE_PLAYER")));
        saveUser(new User(null, "user2", "pass2", 815, getRole("ROLE_PLAYER")));

    }

}
