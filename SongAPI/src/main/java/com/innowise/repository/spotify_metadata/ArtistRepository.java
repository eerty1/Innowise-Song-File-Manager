package com.innowise.repository.spotify_metadata;

import com.innowise.model.spotify_metadata.Artist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("artistRepository")
public interface ArtistRepository extends CrudRepository<Artist, String> {
    @EntityGraph(value = "graph.artistsTracks")
    List<Artist> findAllByIdIn(List<String> ids);
}
