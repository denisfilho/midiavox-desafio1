package com.example.desafio.controllers;

import com.example.desafio.dtos.ExtensionSaveInputDTO;
import com.example.desafio.services.ExtensionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ExtensionControllerTest {

    @Mock
    private ExtensionService extensionService;

    @InjectMocks
    private ExtensionController extensionController;

    public ExtensionControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginExtension_FailUsuarioJaLogadoEmOutraExtension() {
        ExtensionSaveInputDTO input = new ExtensionSaveInputDTO("1234", "userA");

        when(extensionService.loginExtension(input))
                .thenReturn("Fail: Usuário já está logado no ramal: 4321");

        ResponseEntity<String> response = extensionController.loginExtension(input);
        assertEquals("Fail: Usuário já está logado no ramal: 4321", response.getBody());
    }

    @Test
    void testLoginExtension_FailRamalEmUsoPorOutroUsuario() {
        ExtensionSaveInputDTO input = new ExtensionSaveInputDTO("1234", "userA");

        when(extensionService.loginExtension(input))
                .thenReturn("Fail: Ramal já está em uso pelo usuário: outroUsuario");

        ResponseEntity<String> response = extensionController.loginExtension(input);
        assertEquals("Fail: Ramal já está em uso pelo usuário: outroUsuario", response.getBody());
    }

    @Test
    void testLoginExtension_FailRamalNaoEncontradoPeloExtensionNumber() {
        ExtensionSaveInputDTO input = new ExtensionSaveInputDTO("9999", "userA");

        when(extensionService.loginExtension(input))
                .thenReturn("Fail: Ramal não encontrado");

        ResponseEntity<String> response = extensionController.loginExtension(input);
        assertEquals("Fail: Ramal não encontrado", response.getBody());
    }

    @Test
    void testLogoutExtension_FailRamalNaoEncontrado() {
        when(extensionService.logoutExtension("9999"))
                .thenReturn("Fail: Ramal não encontrado");

        ResponseEntity<String> response = extensionController.logoutExtension("9999");
        assertEquals("Fail: Ramal não encontrado", response.getBody());
    }

    @Test
    void testLoginExtension_Success() {
        ExtensionSaveInputDTO input = new ExtensionSaveInputDTO("1234", "userA");

        when(extensionService.loginExtension(input))
                .thenReturn("Success: Login Registrado no Ramal com Sucesso!");

        ResponseEntity<String> response = extensionController.loginExtension(input);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success: Login Registrado no Ramal com Sucesso!", response.getBody());
    }

    @Test
    void testLogoutExtension_Success() {
        when(extensionService.logoutExtension("1234"))
                .thenReturn("Success: Ramal Deslogado com Sucesso!");

        ResponseEntity<String> response = extensionController.logoutExtension("1234");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Success: Ramal Deslogado com Sucesso!", response.getBody());
    }

    @Test
    void testConfigureRange_SuccessWithoutExisting() {
        when(extensionService.configureRange(1000, 1002))
                .thenReturn(List.of()); // nenhum existente

        ResponseEntity<?> response = extensionController.configureRange(1000, 1002);
        assertEquals("Range configurado com sucesso", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testConfigureRange_WithSomeExisting() {
        when(extensionService.configureRange(1000, 1002))
                .thenReturn(List.of("1001", "1002"));

        ResponseEntity<?> response = extensionController.configureRange(1000, 1002);
        assertTrue(response.getBody().toString().contains("Ramais que já haviam sido criados:"));
        assertTrue(response.getBody().toString().contains("1001"));
        assertEquals(200, response.getStatusCodeValue());
    }
}
