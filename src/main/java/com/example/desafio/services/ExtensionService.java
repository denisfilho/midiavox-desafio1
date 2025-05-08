package com.example.desafio.services;

import com.example.desafio.dao.ExtensionRepository;
import com.example.desafio.dtos.ExtensionDisplayedDTO;
import com.example.desafio.entities.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtensionService {

    @Autowired
    private ExtensionRepository extensionRepository;

    Logger logger = LoggerFactory.getLogger(ExtensionService.class);

    public Page<ExtensionDisplayedDTO> getAvailableExtensios(Pageable pageable) {

        Page<ExtensionDisplayedDTO> extensionsDTO = extensionRepository.findByLoggedUserIsNull(pageable)
                .map(extension -> new ExtensionDisplayedDTO(
                        extension.getId(),
                        extension.getLoggedUser(),
                        extension.getExtensionNumber())
                );

        logger.info(extensionsDTO.toString());

        return extensionsDTO;
    }

    public List<String> configureRange(int start, int end) {
        List<String> existingExtensions = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            String extensionNumber = String.valueOf(i);
            Extension findedExtension = extensionRepository.findByExtensionNumber(extensionNumber).orElse(null);

            if (findedExtension != null) {
                existingExtensions.add(extensionNumber);
            } else {
                Extension newExt = new Extension();
                newExt.setExtensionNumber(extensionNumber);
                newExt.setLoggedUser(null);
                extensionRepository.save(newExt);
            }
        }

        return existingExtensions; // Retorna apenas os que já existiam
    }
}
