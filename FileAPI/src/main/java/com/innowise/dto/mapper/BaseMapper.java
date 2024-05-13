package com.innowise.dto.mapper;

import org.mapstruct.Condition;

import java.util.List;

public interface BaseMapper<T, DTO> {

    DTO toDTO(T t);

    T toEntity(DTO dto);

    List<DTO> toDTOs(Iterable<T> entities);

    T updateEntityFromDTO(DTO dto, T t);

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
