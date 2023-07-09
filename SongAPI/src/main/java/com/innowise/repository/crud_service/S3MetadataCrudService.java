package com.innowise.repository.crud_service;

import com.innowise.dto.S3MetadataDTO;
import com.innowise.dto.mapper.S3MetadataMapper;
import com.innowise.model.s3_metadata.S3Metadata;
import com.innowise.repository.S3MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3MetadataCrudService {
    private final S3MetadataRepository s3MetadataRepository;
    private final S3MetadataMapper s3MetadataMapper = S3MetadataMapper.s3MetadataMapperInstance;

    @Autowired
    public S3MetadataCrudService(S3MetadataRepository s3MetadataRepository) {
        this.s3MetadataRepository = s3MetadataRepository;
    }

    public S3MetadataDTO getS3Metadata(Long id) {
        return s3MetadataMapper.toS3MetadataDTO(
                s3MetadataRepository.findById(id).orElseThrow()
        );
    }

    public S3MetadataDTO updateS3Metadata(Long id, S3MetadataDTO s3MetadataDTO) {
        S3Metadata s3MetadataToPatch = s3MetadataRepository.findById(id).orElseThrow();
        return s3MetadataMapper.toS3MetadataDTO(
                s3MetadataRepository.save(
                        s3MetadataMapper.updateS3MetadataFromDTO(s3MetadataDTO, s3MetadataToPatch)
                )
        );
    }

    public void deleteS3Metadata(Long id) {
        s3MetadataRepository.deleteById(id);
    }
}
