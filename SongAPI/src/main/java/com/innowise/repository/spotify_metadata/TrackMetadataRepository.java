package com.innowise.repository.spotify_metadata;

import com.innowise.model.spotify_metadata.TrackMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("trackMetadataRepository")
public interface TrackMetadataRepository extends CrudRepository<TrackMetadata, String> {
}
