package com.example.desafio.services;

import com.example.desafio.dao.ExtensionRepository;
import com.example.desafio.dtos.ExtensionDisplayedDTO;
import com.example.desafio.dtos.ExtensionSaveInputDTO;
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

    // Realiza o login de um usuário em um ramal
    public String loginExtension(ExtensionSaveInputDTO extensionSaveInputDTO) {
        // Verifica se o usuário já está logado em algum ramal
        Extension findedExtensionByUser = extensionRepository.findByLoggedUser(extensionSaveInputDTO.getLoggedUser()).orElse(null);
        logger.info("Verificando se o usuário já está logado em algum ramal");
        if (findedExtensionByUser!= null) {
            return "Fail: Usuário já está logado no ramal: " + findedExtensionByUser.getExtensionNumber();
        }

        // Verifica se o ramal existe
        Extension findedExtensionByExtensionNumber = extensionRepository.findByExtensionNumber(extensionSaveInputDTO.getExtensionNumber()).orElse(null);
        logger.info("Verificando se o ramal existe");
        if (findedExtensionByExtensionNumber == null) {
            return "Fail: Ramal não encontrado";
        }

        // Verifica se o ramal já está em uso por outro usuário
        logger.info("Verificando se o ramal já está em uso por outro usuário");
        if (findedExtensionByExtensionNumber.getLoggedUser() != null) {
            return "Fail: Ramal já está em uso pelo usuário: " + findedExtensionByExtensionNumber.getLoggedUser();
        }

        // Realiza o login
        logger.info("Sucess: Registrando Usuário no Ramal");
        findedExtensionByExtensionNumber.setLoggedUser(extensionSaveInputDTO.getLoggedUser());
        extensionRepository.save(findedExtensionByExtensionNumber);

        return "Success: Login Registrado no Ramal com Sucesso!";
    }

    // Desloga o ramal, removendo o usuário associado
    public String logoutExtension(String extensionNumber) {
        // Verifica se o ramal existe
        Extension findedExtensionByExtensionNumber = extensionRepository.findByExtensionNumber(extensionNumber).orElse(null);
        logger.info("Verificando se o ramal existe");
        if (findedExtensionByExtensionNumber == null) {
            return "Fail: Ramal não encontrado";
        }

        findedExtensionByExtensionNumber.setLoggedUser(null);
        extensionRepository.save(findedExtensionByExtensionNumber);
        logger.info("Success: Ramal Deslogado com Sucesso!");

        return "Success: Ramal Deslogado com Sucesso!";
    }
}