package com.pruebaSermaluc.usuario.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String token;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Phone> phones;

    public User() {
        this.id = UUID.randomUUID().toString();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

    // Getters y Setters
}
