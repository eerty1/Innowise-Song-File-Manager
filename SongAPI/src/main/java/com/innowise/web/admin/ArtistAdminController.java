package com.innowise.web.admin;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.spotify_metadata.ArtistDTO;
import com.innowise.model.spotify_metadata.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/artist")
@RequiredArgsConstructor
public class ArtistAdminController {
    private final CrudService<Artist, ArtistDTO, String> artistCrudService;

    @PatchMapping(path = "/{id}")
    public void updateArtist(@PathVariable String id, @RequestBody ArtistDTO artistDTO) {
        artistCrudService.updateEntity(id, artistDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArtist(@PathVariable String id) {
        artistCrudService.deleteEntity(id);
    }
}
