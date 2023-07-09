package com.innowise.repository;

import com.innowise.model.s3_metadata.S3Metadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3MetadataRepository extends CrudRepository<S3Metadata, Long> {
}
