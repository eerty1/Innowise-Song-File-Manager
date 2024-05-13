package com.innowise.repository;

import com.innowise.model.song.SongFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("songFileRepository")
public interface SongFileRepository extends CrudRepository<SongFile, Long> {

    Boolean existsByArtistAndSongName(String artist, String songName);

    //@Transactional is located here, since on the service layer it is not triggered because of the Camel's @Consume
    @Transactional
    void deleteByArtistAndSongName(String artist, String songName);
}
