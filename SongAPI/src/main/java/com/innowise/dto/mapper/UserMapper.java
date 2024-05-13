package com.innowise.dto.mapper;

import com.innowise.dto.user.UserDTO;
import com.innowise.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component("userMapperImpl")
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE)
public interface UserMapper extends BaseMapper<User, UserDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    User updateEntityFromDTO(UserDTO userDTO, @MappingTarget User user);
}

