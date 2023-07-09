package com.innowise.repository;

import com.innowise.model.spotify_metadata.SpotifyMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotifyMetadataRepository extends CrudRepository<SpotifyMetadata, Long> {
}
