package com.chessplatform.serviceuser.api;


import com.chessplatform.serviceuser.repo.model.User;
import com.chessplatform.serviceuser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign_up")
    public ResponseEntity<Void>signUp(@RequestBody com.chessplatform.serviceuser.api.dto.User user)
    {
        try {
            final long id = userService.createNewUser(user.getUsername(), user.getPassword(), user.getEloRating());
        }
        catch(Exception ex) {
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping("/get_by_name")
    public ResponseEntity<com.chessplatform.serviceuser.api.dto.User> show(@RequestParam String username)
    {
        User user = userService.getUser(username);
        if(user != null) {
            return ResponseEntity.ok(new com.chessplatform.serviceuser.api.dto.User(user));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping ("/get_all")
    public ResponseEntity<List<com.chessplatform.serviceuser.api.dto.User>> showAll()
    {
        List<User> users = userService.getUsers();
        List<com.chessplatform.serviceuser.api.dto.User> usersDto = new ArrayList<>();
        for(User user : users)
            usersDto.add(new com.chessplatform.serviceuser.api.dto.User(user));
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteByUsername(@RequestParam String username)
    {
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("DELETE: User {} does not exist!",username);
            return ResponseEntity.status(409).body(ex.getMessage());
        }
    }

    /*@GetMapping("/delete")
    public ResponseEntity<Void> deleteByUsername(@RequestParam String username,
                                                 @RequestHeader(name="Authorization", defaultValue = "default") String token)
    {
        com.chessplatform.serviceuser.dto.User user = userService.decodeToken(token);
        if(user.getUsername().equals(username) || user.getRole().equals("ROLE_ADMIN")) {
            try {
                userService.deleteUserByUsername(username);
                return ResponseEntity.noContent().build();
            } catch (Exception ex) {
            }
        }
        return ResponseEntity.status(409).build();
    }*/

}
