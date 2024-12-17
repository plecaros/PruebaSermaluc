package com.pruebaSermaluc.usuario.controller;

import com.pruebaSermaluc.usuario.exception.CustomException;
import com.pruebaSermaluc.usuario.model.User;
import com.pruebaSermaluc.usuario.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>("{\"mensaje\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }
}
