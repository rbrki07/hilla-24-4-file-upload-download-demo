package com.example.application.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {

    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);

    public static byte[] readFileFromFilesystem(String filename) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            return fis.readAllBytes();
        } catch (IOException e) {
            logger.error("Could not read file from filesystem.", e);
            throw new RuntimeException(e);
        }
    }

    public static void writeFileToFilesystem(InputStream in, String filename) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filename);
            fos.write(in.readAllBytes());
            fos.close();
        } catch (IOException e) {
            logger.error("Could not write file to filesystem.", e);
            throw new RuntimeException(e);
        }
    }

    public static void deleteFileFromFilesystem(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            logger.error("Could not delete file from filesystem.", e);
            throw new RuntimeException(e);
        }
    }
}
