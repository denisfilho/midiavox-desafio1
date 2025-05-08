package com.example.desafio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "extensions")
@NoArgsConstructor
@AllArgsConstructor
public class Extension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "O extension_number não pode estar vazio")
    @Column(name = "extension_number", nullable = false, unique = true)
    private String extensionNumber;

    @Column(name = "logged_user")
    private String loggedUser;

    public Long getId() {
        return id;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }
}
