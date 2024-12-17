package com.pruebaSermaluc.usuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebaSermaluc.usuario.model.User;
import com.pruebaSermaluc.usuario.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Configurar MockMvc y ObjectMapper
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        // Crear usuario de prueba
        User user = new User();
        user.setName("Juan Perez");
        user.setEmail("juan.perez@example.com");
        user.setPassword("password123");

        // Simular comportamiento del servicio
        when(userService.registerUser(any(User.class))).thenReturn(user);

        // Convertir objeto a JSON
        String userJson = objectMapper.writeValueAsString(user);

        // Ejecutar solicitud POST
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(userJson));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() throws Exception {
        // Configurar excepción en el servicio
        when(userService.registerUser(any(User.class)))
                .thenThrow(new RuntimeException("El correo ya está registrado"));

        User user = new User();
        user.setEmail("juan.perez@example.com");

        String userJson = objectMapper.writeValueAsString(user);

        // Ejecutar solicitud POST
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"mensaje\":\"El correo ya está registrado\"}"));
    }
}
