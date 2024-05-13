package com.innowise.song_file_metadata_collector;

import com.innowise.model.SongFileMetadata;
import com.innowise.model.song_file.SongFile;

import java.io.IOException;

public interface SongFileMetadataCollector {
    SongFileMetadata obtainMetadata(SongFile songFile) throws IOException;

}
