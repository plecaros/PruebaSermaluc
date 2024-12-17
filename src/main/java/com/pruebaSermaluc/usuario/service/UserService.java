package com.pruebaSermaluc.usuario.service;

import com.pruebaSermaluc.usuario.exception.CustomException;
import com.pruebaSermaluc.usuario.model.User;
import com.pruebaSermaluc.usuario.repository.UserRepository;
import com.pruebaSermaluc.usuario.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${regex.email}")
    private String emailRegex;

    @Value("${regex.password}")
    private String passwordRegex;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(User user) {
        // Validar correo electrónico
        if (!Pattern.matches(emailRegex, user.getEmail())) {
            throw new CustomException("El correo no tiene un formato válido");
        }

        // Validar contraseña
        if (!Pattern.matches(passwordRegex, user.getPassword())) {
            throw new CustomException("La contraseña no cumple con los requisitos");
        }

        // Validar si el correo ya existe
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException("El correo ya está registrado");
        }

        // Generar token y guardar el usuario
        user.setToken(jwtUtil.generateToken(user.getEmail()));
        return userRepository.save(user);
    }
}
