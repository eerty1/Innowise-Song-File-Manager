package com.innowise.web;

import com.innowise.dto.S3MetadataDTO;
import com.innowise.repository.crud_service.S3MetadataCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class S3MetadataController {
    private final S3MetadataCrudService s3MetadataCrudService;

    @Autowired
    public S3MetadataController(S3MetadataCrudService s3MetadataCrudService) {
        this.s3MetadataCrudService = s3MetadataCrudService;
    }

    @GetMapping(path = "/get-s3metadata/{id}", produces = APPLICATION_JSON_VALUE)
    public S3MetadataDTO getS3Metadata(@PathVariable Long id) {
        return s3MetadataCrudService.getS3Metadata(id);
    }

    @PatchMapping(path = "/update-s3metadata/{id}", consumes = APPLICATION_JSON_VALUE)
    public S3MetadataDTO updateS3Metadata(@PathVariable Long id, @RequestBody S3MetadataDTO s3MetadataDTO) {
        return s3MetadataCrudService.updateS3Metadata(id, s3MetadataDTO);
    }

    @DeleteMapping(path = "/delete-s3metadata/{id}")
    public void deleteS3Metadata(@PathVariable Long id) {
        s3MetadataCrudService.deleteS3Metadata(id);
    }
}
