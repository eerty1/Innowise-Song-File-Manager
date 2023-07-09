package com.innowise.dto.mapper;

import com.innowise.dto.S3MetadataDTO;
import com.innowise.model.s3_metadata.S3Metadata;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface S3MetadataMapper {
    S3MetadataMapper s3MetadataMapperInstance = Mappers.getMapper(S3MetadataMapper.class);

    S3MetadataDTO toS3MetadataDTO(S3Metadata s3Metadata);

    @Mapping(target = "id", ignore = true)
    S3Metadata updateS3MetadataFromDTO(S3MetadataDTO s3MetadataDTO, @MappingTarget S3Metadata s3MetadataToPatch);

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    @Condition
    default boolean isNotZero(long value) {
        return value != 0;
    }
}
