package com.innowise.dto.mapper;

import com.innowise.dto.song_file.SongFileDTO;
import com.innowise.model.song.SongFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component("songFileMapper")
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface SongFileMapper extends BaseMapper<SongFile, SongFileDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "storageType", ignore = true)
    SongFile updateEntityFromDTO(SongFileDTO songFileDTO, @MappingTarget SongFile songFile);
}
