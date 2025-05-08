package com.example.desafio.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ExtensionSaveInputDTO {

    @NotEmpty
    private String usuario;

    @NotEmpty
    private String extension;

    public String getExtension() {
        return extension;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setExtension(String extension){
        this.extension= extension;
    }

    public void setUsuario(String usuario){
        this.usuario= usuario;
    }
}


