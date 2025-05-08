package com.example.desafio.controllers;


import com.example.desafio.dtos.ExtensionDisplayedDTO;
import com.example.desafio.services.ExtensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
