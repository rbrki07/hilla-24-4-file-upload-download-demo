package com.example.application.api.rest;

import com.example.application.entities.File;
import com.example.application.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileService fileService;

    @PostMapping("/files")
    public ResponseEntity<File> upload(@RequestParam("file") MultipartFile multipartFile) {
        logger.debug("Uploading file '" + multipartFile.getOriginalFilename() + "'");
        try {
            File file = fileService.save(multipartFile);
            return ResponseEntity.ok().body(file);
        } catch (Exception e) {
            logger.error("Error uploading file.", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        logger.debug("Downloading file '" + filename + "'");
        Optional<File> file = fileService.read(filename);
        if (file.isPresent()) {
            Resource resource = fileService.readFile(filename);
            if (resource != null) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.get().getOriginalFilename() + "\"").body(resource);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
