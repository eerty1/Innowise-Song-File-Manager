package com.innowise.crud_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.dto.song_file.DeleteSongFileDTO;
import com.innowise.exception.FailedDeletionException;
import com.innowise.model.song.SongFile;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CrudUtils {
    private final ObjectMapper objectMapper;
    private final ProducerTemplate producerTemplate;

    @Value("${camel.songFileDeletionToSongAPIQueue}")
    private String songFileDeletionToSongAPIQueue;

    protected void sendDeletionNotification(SongFile songFile) {
        String deleteSongFileDTOJson;

        try {
            deleteSongFileDTOJson = objectMapper.writeValueAsString(
                    new DeleteSongFileDTO(songFile.getArtist(), songFile.getSongName())
            );
        } catch (JsonProcessingException jsonProcessingException) {
            throw new FailedDeletionException("The chosen song wasn't deleted");
        }

        producerTemplate.sendBody(
                songFileDeletionToSongAPIQueue,
                deleteSongFileDTOJson
        );
    }

    protected SongFile createSongInstance(MultipartFile songFile) {
        String[] artistAndSongName = FilenameUtils.getBaseName(songFile.getOriginalFilename()).split("-");
        return new SongFile(
                artistAndSongName[0].trim(),
                artistAndSongName[1].trim()
        );
    }
}
