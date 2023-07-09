package com.innowise.repository.crud_service;

import com.innowise.dto.SongFileDTO;
import com.innowise.dto.mapper.SongFileMapper;
import com.innowise.model.song_file.SongFile;
import com.innowise.repository.SongFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongFileCrudService {
    private final SongFileRepository songFileRepository;
    private final SongFileMapper songFileMapper = SongFileMapper.songFileMapperInstance;

    @Autowired
    public SongFileCrudService(SongFileRepository songFileRepository) {
        this.songFileRepository = songFileRepository;
    }

    public Iterable<SongFileDTO> getSongFiles() {
        return songFileMapper.toSongFileDTOs(
                songFileRepository.findAll()
        );
    }

    public SongFileDTO updateSongFile(Long id, SongFileDTO songFileDTO) {
        SongFile songFileToPatch = songFileRepository.findById(id).orElseThrow();
        return songFileMapper.toSongFileDTO(
                songFileRepository.save(
                        songFileMapper.updateSongFileFromDTO(songFileDTO, songFileToPatch)
                )
        );
    }

    public void deleteSongFile(Long id) {
        songFileRepository.deleteById(id);
    }
}