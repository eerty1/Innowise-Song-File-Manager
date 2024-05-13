package com.innowise.dto.mapper.spotify_metadata;

import com.innowise.dto.mapper.BaseMapper;
import com.innowise.dto.spotify_metadata.ArtistDTO;
import com.innowise.model.spotify_metadata.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component("artistMapperImpl")
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface ArtistMapper extends BaseMapper<Artist, ArtistDTO> {
    @Override
    @Mapping(target = "id", ignore = true)
    Artist updateEntityFromDTO(ArtistDTO artistDTO, @MappingTarget Artist artist);
}

