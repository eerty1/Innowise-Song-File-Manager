package com.innowise.crud_service;

import com.innowise.dto.mapper.BaseMapper;
import com.innowise.dto.user.UserDTO;
import com.innowise.model.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class UserCrudService extends CrudService<User, UserDTO, String> {

    public UserCrudService(CrudRepository<User, String> crudRepository,
                           @Qualifier("userMapperImpl") BaseMapper<User, UserDTO> baseMapper)
    {
        super(crudRepository, baseMapper);
    }
}
