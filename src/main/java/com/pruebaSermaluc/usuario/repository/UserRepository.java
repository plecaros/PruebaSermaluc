package com.pruebaSermaluc.usuario.repository;

import com.pruebaSermaluc.usuario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
