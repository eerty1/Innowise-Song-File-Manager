package com.innowise.crud_service.spotify_metadata;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.mapper.BaseMapper;
import com.innowise.dto.spotify_metadata.ArtistDTO;
import com.innowise.model.spotify_metadata.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service("artistCrudService")
public class ArtistCrudService extends CrudService<Artist, ArtistDTO, String> {

    @Autowired
    public ArtistCrudService(CrudRepository<Artist, String> crudRepository,
                             @Qualifier("artistMapperImpl") BaseMapper<Artist, ArtistDTO> baseMapper)
    {
        super(crudRepository, baseMapper);
    }
}
