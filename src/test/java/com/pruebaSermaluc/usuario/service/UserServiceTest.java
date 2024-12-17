package com.pruebaSermaluc.usuario.service;

import com.pruebaSermaluc.usuario.model.User;
import com.pruebaSermaluc.usuario.repository.UserRepository;
import com.pruebaSermaluc.usuario.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear un usuario de prueba
        user = new User();
        user.setName("Juan Perez");
        user.setEmail("juan.perez@example.com");
        user.setPassword("password123");
    }

    @Test
    void testRegisterUser_Success() {
        // Configurar comportamiento de mocks
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(jwtUtil.generateToken(user.getEmail())).thenReturn("mocked-token");

        // Ejecutar el método
        User registeredUser = userService.registerUser(user);

        // Verificar resultados
        assertNotNull(registeredUser);
        assertEquals("mocked-token", registeredUser.getToken());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        // Configurar comportamiento cuando el correo ya existe
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // Ejecutar y verificar que se lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
