package com.innowise.dto.mapper;

import com.innowise.dto.SongFileMetadataDTO;
import com.innowise.model.SongFileMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component("songFileMetadataMapperImpl")
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface SongFileMetadataMapper extends BaseMapper<SongFileMetadata, SongFileMetadataDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    SongFileMetadata updateEntityFromDTO(SongFileMetadataDTO songFileMetadataDTO, @MappingTarget SongFileMetadata songFileMetadata);
}
