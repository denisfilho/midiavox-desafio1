package com.example.desafio.controllers;


import com.example.desafio.dtos.ExtensionDisplayedDTO;
import com.example.desafio.dtos.ExtensionSaveInputDTO;
import com.example.desafio.services.ExtensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/extensions")
public class ExtensionController {

    @Autowired
    private ExtensionService extensionService;

    Logger logger = LoggerFactory.getLogger(ExtensionController.class);

    @GetMapping("/available")
    public ResponseEntity<Page<ExtensionDisplayedDTO>> listAvailableExtensios(Pageable pageable) {

        logger.info("chamando método para pegar todos os ramais disponíveis");
        Page<ExtensionDisplayedDTO> avaibleExtensionsDTO = extensionService.getAvailableExtensios(pageable);

        if (avaibleExtensionsDTO.isEmpty()) {
            logger.info("Nenhum ramal disponível");
            return ResponseEntity.noContent().build(); // status 204
        }
        return ResponseEntity.ok(extensionService.getAvailableExtensios(pageable));
    }

    @PostMapping("/range")
    public ResponseEntity<?> configureRange(@RequestParam int start, @RequestParam int end) {

        logger.info("chamando método para criar os ramais com base no range");
        List<String> alreadyCreated = extensionService.configureRange(start, end);

        if (alreadyCreated.isEmpty()) {
            return ResponseEntity.ok("Range configurado com sucesso");
        } else {
            return ResponseEntity.ok("Ramais que já haviam sido criados: " + alreadyCreated);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginExtension(@RequestBody ExtensionSaveInputDTO extensionSaveInputDTO) {
        logger.info("chamando método para ligar o ramal ao usuário");
        return ResponseEntity.ok(extensionService.loginExtension(extensionSaveInputDTO));
    }

    @DeleteMapping("/logout/{extensionNumber}")
    public ResponseEntity<String> logoutExtension(@PathVariable String extensionNumber) {
        return ResponseEntity.ok(extensionService.logoutExtension(extensionNumber));
    }
}
