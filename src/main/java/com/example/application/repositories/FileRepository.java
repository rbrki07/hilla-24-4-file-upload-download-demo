package com.example.application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.application.entities.File;

public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {

    Optional<File> findOneByFilename(String filename);
}
