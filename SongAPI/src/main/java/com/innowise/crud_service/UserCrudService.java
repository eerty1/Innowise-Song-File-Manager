package com.innowise.crud_service;

import com.innowise.dto.user.UserDTO;
import com.innowise.dto.mapper.BaseMapper;
import com.innowise.model.user.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service
public class UserCrudService extends CrudService<User, UserDTO, String> {
    public UserCrudService(CrudRepository<User, String> crudRepository,
                           @Qualifier("userMapperImpl") BaseMapper<User, UserDTO> mapper)
    {
        super(crudRepository, mapper);
    }










































    //    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public void saveUser(User user) {
//        todo Конкретно тут никаких проверок я так понимаю не надо, потому что если до этого метода дошло,
//        todo то это значит, что юзера в базе нет НИКАК
//        userRepository.save(user.toUser(passwordEncoder));
//    }
//
//    todo Ну и тут тоже ниже
//    public ResponseEntity<UserDTO> updateUser(Long id, UserDTO userDTO) {
//        User userToUpdate = userRepository.findById(id).orElseThrow();
//        userRepository.save(userDTO.toUser(passwordEncoder));
//        userRepository.save(userMapper.updateUserFromDTO(userDTO, userToUpdate));
//        return new ResponseEntity<>(userMapper.toDTO(userToUpdate), OK);
//    }
//
//
//    public ResponseEntity<String> deleteUser(Long id) {
//        todo Придумать как тут обработать отсутствие юзера, например, кидать NoSuchElementException
//        if (userRepository.existsByUsername(userRepository.findById(id).orElseThrow().getUsername())) {
//            userRepository.deleteById(id);
//            return new ResponseEntity<>("Deleted successfully", OK);
//        }
//        return new ResponseEntity<>("Nothing to delete", OK);

}