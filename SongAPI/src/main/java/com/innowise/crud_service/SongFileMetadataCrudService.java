package com.innowise.crud_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.dto.DeleteMetadataDTO;
import com.innowise.dto.SongFileMetadataDTO;
import com.innowise.dto.mapper.BaseMapper;
import com.innowise.exception.EntityNotFoundException;
import com.innowise.model.SongFileMetadata;
import com.innowise.repository.SongFileMetadataRepository;
import org.apache.camel.Consume;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service("songFileMetadataCrudService")
public class SongFileMetadataCrudService extends DeletionService<SongFileMetadata, SongFileMetadataDTO, String> {

    public SongFileMetadataCrudService(CrudRepository<SongFileMetadata, String> crudRepository,
                                       @Qualifier("songFileMetadataMapperImpl") BaseMapper<SongFileMetadata, SongFileMetadataDTO> baseMapper,
                                       ProducerTemplate producerTemplate,
                                       ObjectMapper objectMapper)
    {
        super(crudRepository, baseMapper, producerTemplate, objectMapper);
    }

    @Consume("aws2-sqs://songFileDeletionToSongAPI?amazonSQSClient=#sqsClient&autoCreateQueue=true")
    public void deleteMetadata(String deleteMetadataDTOJson) throws JsonProcessingException {
        DeleteMetadataDTO deleteMetadataDTO = objectMapper.readValue(deleteMetadataDTOJson, DeleteMetadataDTO.class);
        ((SongFileMetadataRepository) crudRepository).deleteByArtistAndSongName(
                deleteMetadataDTO.getArtist(),
                deleteMetadataDTO.getSongName()
        );
    }

    @Override
    public void deleteEntity(String id) {
        SongFileMetadata songFileMetadata = crudRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        sendDeletionNotification(
                songFileMetadata.getArtist(),
                songFileMetadata.getSongName()
        );
        crudRepository.delete(songFileMetadata);
    }
}
