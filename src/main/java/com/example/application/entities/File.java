package com.example.application.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@EntityListeners(FileListener.class)
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @NotBlank
    private String filename;

    @NotBlank
    private String originalFilename;

    @NotBlank
    private String contentType;

    @NotNull
    private Long size;

    @NotNull
    private LocalDate created;

    public File() {
    }

    public File(MultipartFile file) {
        this.filename = UUID.randomUUID() + getFileExtension(file);
        this.originalFilename = file.getOriginalFilename();
        this.contentType = file.getContentType();
        this.size = file.getSize();
        this.created = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    private String getFileExtension(MultipartFile file) {
        if (file.getOriginalFilename() != null) {
            return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } else {
            return "";
        }
    }
}
