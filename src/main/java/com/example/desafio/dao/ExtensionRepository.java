package com.example.desafio.dao;

import com.example.desafio.entities.Extension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtensionRepository extends JpaRepository<Extension, Long> {

    Page<Extension> findByLoggedUserIsNull(Pageable pageable);

    Optional<Extension> findByExtensionNumber(String extensionNumber);
}
