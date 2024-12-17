package com.pruebaSermaluc.usuario.service;

import com.pruebaSermaluc.usuario.exception.CustomException;
import com.pruebaSermaluc.usuario.model.User;
import com.pruebaSermaluc.usuario.repository.UserRepository;
import com.pruebaSermaluc.usuario.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException("El correo ya está registrado");
        }

        user.setToken(jwtUtil.generateToken(user.getEmail()));
        return userRepository.save(user);
    }
}
