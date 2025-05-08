package com.example.desafio.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ExtensionDisplayedDTO {

    private Long id;

    private String extensionNumber;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setExtensionNumber(String extensionNumber){
        this.extensionNumber= extensionNumber;
    }

    public void setLoggedUser(String loggedUser){
        this.loggedUser= loggedUser;
    }

}
