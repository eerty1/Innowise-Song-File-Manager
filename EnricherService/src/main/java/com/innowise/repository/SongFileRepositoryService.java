package com.innowise.repository;

import com.innowise.model.song_file.SongFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongFileRepositoryService {
    private final SongFileRepository songFileRepository;

    @Autowired
    public SongFileRepositoryService(SongFileRepository songFileRepository) {
        this.songFileRepository = songFileRepository;
    }

    public SongFile getSongFileProperties(Long id) {
        return songFileRepository.findById(id).orElseThrow();
    }
}
