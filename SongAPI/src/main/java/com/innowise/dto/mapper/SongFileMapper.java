package com.innowise.dto.mapper;

import com.innowise.dto.SongFileDTO;
import com.innowise.model.song_file.SongFile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SongFileMapper {
    SongFileMapper songFileMapperInstance = Mappers.getMapper(SongFileMapper.class);

    Iterable<SongFileDTO> toSongFileDTOs(Iterable<SongFile> songFiles);

    SongFileDTO toSongFileDTO(SongFile songFile);

    @Mapping(target = "id", ignore = true)
    SongFile updateSongFileFromDTO(SongFileDTO songFileDTO, @MappingTarget SongFile SongFileToPatch);

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
