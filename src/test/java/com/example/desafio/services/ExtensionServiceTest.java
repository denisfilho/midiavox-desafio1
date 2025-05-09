package com.example.desafio.services;

import com.example.desafio.dao.ExtensionRepository;
import com.example.desafio.dtos.ExtensionSaveInputDTO;
import com.example.desafio.entities.Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExtensionServiceTest {

    @InjectMocks
    private ExtensionService extensionService;

    @Mock
    private ExtensionRepository extensionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginExtension_Fail_UsuarioJaLogadoEmOutraExtension() {
        ExtensionSaveInputDTO input = new ExtensionSaveInputDTO("1234", "userA");

        Extension alreadyLogged = new Extension();
        alreadyLogged.setExtensionNumber("4321");
        alreadyLogged.setLoggedUser("userA");

        when(extensionRepository.findByLoggedUser("userA"))
                .thenReturn(Optional.of(alreadyLogged));

        String result = extensionService.loginExtension(input);
        assertEquals("Fail: Usuário já está logado no ramal: 4321", result);
    }

    @Test
    void testLoginExtension_Fail_RamalNaoEncontradoPeloExtensionNumber() {
        ExtensionSaveInputDTO input = new ExtensionSaveInputDTO("9999", "userA");

        when(extensionRepository.findByLoggedUser("userA"))
                .thenReturn(Optional.empty());

        when(extensionRepository.findByExtensionNumber("9999"))
                .thenReturn(Optional.empty());

        String result = extensionService.loginExtension(input);
        assertEquals("Fail: Ramal não encontrado", result);
    }

    @Test
    void testLoginExtension_Fail_RamalEmUsoPorOutroUsuario() {
        ExtensionSaveInputDTO input = new ExtensionSaveInputDTO("1234", "userA");

        when(extensionRepository.findByLoggedUser("userA")).thenReturn(Optional.empty());

        Extension ramal = new Extension();
        ramal.setExtensionNumber("1234");
        ramal.setLoggedUser("outroUsuario");

        when(extensionRepository.findByExtensionNumber("1234"))
                .thenReturn(Optional.of(ramal));

        String result = extensionService.loginExtension(input);
        assertEquals("Fail: Ramal já está em uso pelo usuário: outroUsuario", result);
    }

    @Test
    void testLogoutExtension_Fail_RamalNaoEncontrado() {
        when(extensionRepository.findByExtensionNumber("9999"))
                .thenReturn(Optional.empty());

        String result = extensionService.logoutExtension("9999");
        assertEquals("Fail: Ramal não encontrado", result);
    }

    @Test
    void testLoginExtension_Success() {
        ExtensionSaveInputDTO input = new ExtensionSaveInputDTO("1234", "userA");

        when(extensionRepository.findByLoggedUser("userA"))
                .thenReturn(Optional.empty());

        Extension extension = new Extension();
        extension.setExtensionNumber("1234");
        extension.setLoggedUser(null);

        when(extensionRepository.findByExtensionNumber("1234"))
                .thenReturn(Optional.of(extension));

        String result = extensionService.loginExtension(input);
        assertEquals("Success: Login Registrado no Ramal com Sucesso!", result);
        verify(extensionRepository).save(extension);
        assertEquals("userA", extension.getLoggedUser());
    }

    @Test
    void testLogoutExtension_Success() {
        Extension extension = new Extension();
        extension.setExtensionNumber("1234");
        extension.setLoggedUser("userA");

        when(extensionRepository.findByExtensionNumber("1234"))
                .thenReturn(Optional.of(extension));

        String result = extensionService.logoutExtension("1234");
        assertEquals("Success: Ramal Deslogado com Sucesso!", result);
        verify(extensionRepository).save(extension);
        assertNull(extension.getLoggedUser());
    }

    @Test
    void testConfigureRange_Success() {
        when(extensionRepository.findByExtensionNumber("1001"))
                .thenReturn(Optional.empty());

        when(extensionRepository.findByExtensionNumber("1002"))
                .thenReturn(Optional.of(new Extension())); // Já existe

        when(extensionRepository.findByExtensionNumber("1003"))
                .thenReturn(Optional.empty());

        List<String> result = extensionService.configureRange(1001, 1003);
        assertEquals(1, result.size());
        assertTrue(result.contains("1002"));
        verify(extensionRepository, times(2)).save(any(Extension.class)); // 1001 e 1003 criados
    }
}