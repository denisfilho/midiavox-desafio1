package com.example.desafio.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ExtensionSaveInputDTO {

    @NotEmpty
    private String extensionNumber;

    @NotEmpty
    private String loggedUser;

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setExtensionNumber(String extensionNumber){
        this.extensionNumber= extensionNumber;
    }

    public void setLoggedUser(String loggedUser){
        this.loggedUser= loggedUser;
    }
}


