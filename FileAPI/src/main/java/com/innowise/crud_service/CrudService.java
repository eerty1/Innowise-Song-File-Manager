package com.innowise.crud_service;

import com.innowise.dto.mapper.BaseMapper;
import com.innowise.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class CrudService<T, DTO, ID> {
    protected final CrudRepository<T, ID> crudRepository;
    protected final BaseMapper<T, DTO> baseMapper;

    public DTO getEntity(ID id) {
        return baseMapper.toDTO(
                crudRepository.findById(id).orElseThrow(EntityNotFoundException::new)
        );
    }

    public List<DTO> getEntities() {
        return baseMapper.toDTOs(crudRepository.findAll());
    }

    public void addEntity(DTO dto) {
        crudRepository.save(baseMapper.toEntity(dto));
    }

    public void updateEntity(ID id, DTO dto) {
        crudRepository.save(
                baseMapper.updateEntityFromDTO(
                        dto, crudRepository.findById(id).orElseThrow(EntityNotFoundException::new)
                )
        );
    }

    public void deleteEntity(ID id) {
        crudRepository.deleteById(id);
    }
}
