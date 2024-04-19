package com.example.application.entities;

import com.example.application.services.FileStorageService;
import jakarta.persistence.PostRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileListener {

    @Autowired
    FileStorageService fileStorageService;

    @PostRemove
    private void afterDelete(File file) {
        fileStorageService.delete(file.getFilename());
    }
}
