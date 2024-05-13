package com.innowise.crud_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.dto.DeleteMetadataDTO;
import com.innowise.dto.mapper.BaseMapper;
import com.innowise.exception.FailedDeletionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.data.repository.CrudRepository;

public abstract class DeletionService<T, DTO, ID> extends CrudService<T, DTO, ID> {

    @Produce("aws2-sqs://metadataDeletionToFileAPI?amazonSQSClient=#sqsClient")
    protected final ProducerTemplate producerTemplate;
    protected final ObjectMapper objectMapper;

    public DeletionService(CrudRepository<T, ID> crudRepository, BaseMapper<T, DTO> baseMapper,
                           ProducerTemplate producerTemplate,
                           ObjectMapper objectMapper)
    {
        super(crudRepository, baseMapper);
        this.producerTemplate = producerTemplate;
        this.objectMapper = objectMapper;
    }

    protected void sendDeletionNotification(String artist, String songName) {
        String deleteSongFileDTOJson;

        try {
            deleteSongFileDTOJson = objectMapper.writeValueAsString(
                    new DeleteMetadataDTO(artist, songName)
            );
        } catch (JsonProcessingException jsonProcessingException) {
            throw new FailedDeletionException("The chosen song wasn't deleted");
        }

        producerTemplate.sendBody(deleteSongFileDTOJson);
    }
}
