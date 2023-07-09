package com.innowise.repository;

import com.innowise.model.song_file.SongFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongFileRepository extends CrudRepository<SongFile, Long> {
}
