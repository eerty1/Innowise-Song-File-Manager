package com.innowise.dto.mapper;

import com.innowise.dto.UpdateUserDTO;
import com.innowise.dto.UserDTO;
import com.innowise.dto.encoded_mapping.EncodedMapping;
import com.innowise.dto.encoded_mapping.PasswordEncoderMapper;
import com.innowise.model.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component("userMapperImpl")

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, uses = PasswordEncoderMapper.class)
public interface UserMapper {


    @Named("userDTOWithoutPassword")
    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User user);

    UserDTO toDTOWithPassword(User user);

    @Mapping(target = "password", qualifiedBy = EncodedMapping.class)
    User toEntity(UserDTO userDTO);
    @IterableMapping(qualifiedByName = "userDTOWithoutPassword")
    List<UserDTO> toDTOs(Iterable<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User updateUserFromUserDTO(UpdateUserDTO updateUserDTO, @MappingTarget User userToUpdate);

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}

