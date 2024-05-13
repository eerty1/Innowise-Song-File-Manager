package com.innowise.crud_service.spotify_metadata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.crud_service.DeletionService;
import com.innowise.dto.spotify_metadata.TrackMetadataDTO;
import com.innowise.dto.mapper.BaseMapper;
import com.innowise.exception.EntityNotFoundException;
import com.innowise.model.spotify_metadata.TrackMetadata;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service("trackMetadataCrudService")
public class TrackMetadataCrudService extends DeletionService<TrackMetadata, TrackMetadataDTO, String> {

    @Autowired
    public TrackMetadataCrudService(CrudRepository<TrackMetadata, String> crudRepository,
                                    @Qualifier("trackMetadataMapperImpl") BaseMapper<TrackMetadata, TrackMetadataDTO> baseMapper,
                                    ProducerTemplate producerTemplate,
                                    ObjectMapper objectMapper)
    {
        super(crudRepository, baseMapper, producerTemplate, objectMapper);
    }

    @Override
    public void deleteEntity(String id) {
        TrackMetadata trackMetadata = crudRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        sendDeletionNotification(
                trackMetadata.getSongFileMetadata().getArtist(),
                trackMetadata.getSongFileMetadata().getSongName()
        );
        crudRepository.delete(trackMetadata);
    }
}
