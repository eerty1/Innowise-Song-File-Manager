package com.innowise.crud_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.dto.mapper.BaseMapper;
import com.innowise.dto.song_file.DeleteSongFileDTO;
import com.innowise.dto.song_file.SongFileDTO;
import com.innowise.exception.EntityNotFoundException;
import com.innowise.model.song.SongFile;
import com.innowise.repository.SongFileRepository;
import com.innowise.storage_service.StorageService;
import org.apache.camel.Consume;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

@Service("songFileCrudService")
public class SongFileCrudService extends CrudService<SongFile, SongFileDTO, Long> {
    private final SongFileRepository songFileRepository = (SongFileRepository) crudRepository;
    private final CrudUtils crudUtils;
    private final StorageService storageService;
    private final ProducerTemplate producerTemplate;
    private final ObjectMapper objectMapper;
    private final String songFileToEnricherServiceQueue;


    @Autowired
    public SongFileCrudService(CrudRepository<SongFile, Long> crudRepository,
                               @Qualifier("songFileMapperImpl") BaseMapper<SongFile, SongFileDTO> baseMapper,
                               CrudUtils crudUtils, StorageService storageService,
                               ProducerTemplate producerTemplate,
                               ObjectMapper objectMapper,
                               @Value("${camel.songFileToEnricherServiceQueue}") String songFileToEnricherServiceQueue)
    {
        super(crudRepository, baseMapper);
        this.crudUtils = crudUtils;
        this.storageService = storageService;
        this.producerTemplate = producerTemplate;
        this.objectMapper = objectMapper;
        this.songFileToEnricherServiceQueue = songFileToEnricherServiceQueue;
    }

    @Transactional
    public ResponseEntity<String> persistSongFile(MultipartFile songFile) throws IOException {
        SongFile song = crudUtils.createSongInstance(songFile);
        if (songFileRepository.existsByArtistAndSongName(song.getArtist(), song.getSongName())) {
            return new ResponseEntity<>("Song " + song.getSongName() + " by " + song.getArtist() + " already exists", CONFLICT);
        }
        crudRepository.save(storageService.saveFile(songFile, song));
        producerTemplate.sendBody(songFileToEnricherServiceQueue, song.getId());
        return new ResponseEntity<>("Successfully persisted", OK);
    }

    @Override
    public void deleteEntity(Long id) {
        SongFile songFile = crudRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        crudUtils.sendDeletionNotification(songFile);
        storageService.deleteFile(songFile);
        crudRepository.delete(songFile);
    }

    @Consume("aws2-sqs://metadataDeletionToFileAPI?amazonSQSClient=#sqsClient&autoCreateQueue=true")
    public void deleteSongFile(String deleteSongFileDTOJson) throws JsonProcessingException {
        DeleteSongFileDTO deleteSongFileDTO = objectMapper.readValue(deleteSongFileDTOJson, DeleteSongFileDTO.class);
        songFileRepository.deleteByArtistAndSongName(
                deleteSongFileDTO.getArtist(),
                deleteSongFileDTO.getSongName()
        );
    }
}
