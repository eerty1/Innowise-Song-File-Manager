package com.innowise.repository;

import com.innowise.model.SongFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongFileRepository extends CrudRepository<SongFile, Long> {
}
