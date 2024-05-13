package com.innowise.repository;

import com.innowise.model.SongFileMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("songFileMetadataRepository")
public interface SongFileMetadataRepository extends CrudRepository<SongFileMetadata, String> {

    //@Transactional is located here, since on the service layer it is not triggered because of the Camel's @Consume
    @Transactional
    void deleteByArtistAndSongName(String artist, String songName);
}
