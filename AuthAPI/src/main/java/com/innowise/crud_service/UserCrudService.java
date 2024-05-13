package com.innowise.crud_service;

import com.innowise.dto.ChangeUserPasswordDTO;
import com.innowise.dto.CheckUserOutDTO;
import com.innowise.dto.UpdateUserDTO;
import com.innowise.dto.UserDTO;
import com.innowise.dto.mapper.UserMapper;
import com.innowise.jwt.JwtService;
import com.innowise.model.User;
import com.innowise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.innowise.crud_service.MicroserviceUrl.FILE_API;
import static com.innowise.crud_service.MicroserviceUrl.SONG_API;
import static com.innowise.model.Role.MICROSERVICE;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@Service
public class UserCrudService {

    private final WebClient.Builder webClientBuilder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCrudService(@LoadBalanced WebClient.Builder webClientBuilder,
                           UserRepository userRepository,
                           UserMapper userMapper,
                           JwtService jwtService,
                           PasswordEncoder passwordEncoder)
    {
        this.webClientBuilder = webClientBuilder;
        this.userRepository= userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers() {
        return userMapper.toDTOs(userRepository.findAll());
    }

    public UserDTO getUser(UUID id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow());
    }

    public ResponseEntity<UserDTO> checkUserOut(CheckUserOutDTO checkUserOutDTO) {
        Optional<User> user = userRepository.findById(UUID.fromString(checkUserOutDTO.getId()));
        if (user.isPresent()) {
            manipulateMicroserviceUsers(user.get(), POST, "http://" + checkUserOutDTO.getMicroserviceName() + "/api/user");
            return new ResponseEntity<>(userMapper.toDTO(user.get()), OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<String> registerNewUser(UserDTO userDTO) {
        if (!userRepository.existsByUsername(userDTO.getUsername())) {
            User user = userMapper.toEntity(userDTO);
            userRepository.save(user);
            manipulateMicroserviceUsers(user, POST, FILE_API.getUrl(), SONG_API.getUrl());
            return new ResponseEntity<>(jwtService.generateToken(user, DAYS), CREATED);
        } else {
            return new ResponseEntity<>("User " + userDTO.getUsername() + " already exists", CONFLICT);

        }
    }

    public void updateUser(UUID id, UpdateUserDTO updateUserDTO) {
        User updatedUser = userMapper.updateUserFromUserDTO(
                updateUserDTO,
                userRepository.findById(id).orElseThrow()
        );
        userRepository.save(updatedUser);
        manipulateMicroserviceUsers(updatedUser, PATCH, FILE_API.getUrl() + id, SONG_API.getUrl() + id);
    }

    public ResponseEntity<String> changeUserPassword(UUID id, ChangeUserPasswordDTO changeUserPasswordDTO) {
        User userToUpdate = userRepository.findById(id).orElseThrow();
        if (passwordEncoder.matches(changeUserPasswordDTO.getOldPassword(), userToUpdate.getPassword())) {
            userToUpdate.setPassword(passwordEncoder.encode(changeUserPasswordDTO.getNewPassword()));
            userRepository.save(userToUpdate);
            manipulateMicroserviceUsers(userToUpdate, PATCH, FILE_API.getUrl() + id, SONG_API.getUrl() + id);
            return new ResponseEntity<>("Your password is changed successfully", OK);
        } else {
            return new ResponseEntity<>("Old password is not correct", UNAUTHORIZED);
        }
    }


    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
        manipulateMicroserviceUsers(
                new User(id, MICROSERVICE.name(), MICROSERVICE),
                DELETE,
                FILE_API.getUrl() + id,
                SONG_API.getUrl() + id);
    }

    /*
    WebClientResponseException.BadRequest is ignored, since it doesn't affect business logic
    if a microservice doesn't have a particular instance of a user, which obviously exists in AuthAPI database
    this instance will be eventually created, when the microservice is accessed with a token for that user
     */
    private void manipulateMicroserviceUsers(User user, HttpMethod httpMethod, String ... urls) {
        Mono<UserDTO> body = user != null ? Mono.just(userMapper.toDTOWithPassword(user)) : Mono.empty();
        Flux.fromArray(urls)
                .flatMap(url ->
                        webClientBuilder.build()
                                .method(httpMethod)
                                .uri(url)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtService.generateTokenInternalRequest())
                                .body(body, User.class)
                                .exchangeToMono(response -> Mono.just(Void.class))
                                .onErrorResume(WebClientResponseException.BadRequest.class, error -> Mono.just(Void.class))
                )
                .subscribe();
    }
}
