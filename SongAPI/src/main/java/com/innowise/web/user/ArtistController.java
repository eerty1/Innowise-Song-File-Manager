package com.innowise.web.user;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.spotify_metadata.ArtistDTO;
import com.innowise.model.spotify_metadata.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final CrudService<Artist, ArtistDTO, String> artistCrudService;

    @GetMapping(path = "/{id}")
    public ArtistDTO getArtist(@PathVariable String id) {
        return artistCrudService.getEntity(id);
    }

    @GetMapping
    public List<ArtistDTO> getArtists() {
        return artistCrudService.getEntities();
    }
}
