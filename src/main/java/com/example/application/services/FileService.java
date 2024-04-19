package com.example.application.services;

import com.example.application.entities.File;
import com.example.application.repositories.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Component
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileStorageService fileStorageService;

    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public File save(MultipartFile multipartFile) {
        File file = new File(multipartFile);
        logger.debug("Saving " + file + " to database.");
        fileRepository.save(file);
        try {
            fileStorageService.save(multipartFile.getInputStream(), file.getFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public Resource readFile(String filename) {
        return new ByteArrayResource(fileStorageService.read(filename));
    }

    public Optional<File> read(String filename) {
        logger.debug("Reading file with file '" + filename + "' from database.");
        return fileRepository.findOneByFilename(filename);
    }
}
