package com.innowise.repository;

import com.innowise.model.SongFile;
import com.innowise.model.StorageType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SongFileRepositoryService {
    private final SongFileRepository songFileRepository;

    @Autowired
    public SongFileRepositoryService(SongFileRepository songFileRepository) {
        this.songFileRepository = songFileRepository;
    }

    public SongFile addSongToDatabase(MultipartFile songFile, String storagePath, StorageType storageType) {
        String[] artistAndSongName = splitSongName(FilenameUtils.getBaseName(songFile.getOriginalFilename()));
        return songFileRepository.save(
                new SongFile(
                        artistAndSongName[0].trim(),
                        artistAndSongName[1].trim(),
                        storageType,
                        storagePath
                )
        );
    }

    private String[] splitSongName(String songName) {
        return songName.split("-");
    }
}
