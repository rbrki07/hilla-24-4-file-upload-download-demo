package com.example.application.services;

import com.example.application.handler.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FileStorageService {

    @Value("${storage.file.path}")
    private String path;

    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    public void save(InputStream is, String filename) {
        logger.debug("Saving file '" + filename + "' to path '" + path + "'");
        FileHandler.writeFileToFilesystem(is, path + "/" + filename);
    }

    public byte[] read(String filename) {
        logger.debug("Reading file '" + filename + "' from path '" + path + "'");
        return FileHandler.readFileFromFilesystem(path + "/" + filename);
    }

    public void delete(String filename) {
        logger.debug("Deleting file '" + filename + "' from path '" + path + "'");
        FileHandler.deleteFileFromFilesystem(path + "/" + filename);
    }
}
