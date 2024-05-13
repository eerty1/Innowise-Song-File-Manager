package com.innowise.dto.mapper.spotify_metadata;

import com.innowise.dto.mapper.BaseMapper;
import com.innowise.dto.spotify_metadata.TrackMetadataDTO;
import com.innowise.model.spotify_metadata.TrackMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;


import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component("trackMetadataMapperImpl")
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface TrackMetadataMapper extends BaseMapper<TrackMetadata, TrackMetadataDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artists", ignore = true)
    TrackMetadata updateEntityFromDTO(TrackMetadataDTO trackMetadataDTO, @MappingTarget TrackMetadata trackMetadata);
}
