package com.kabgig.rest.websevices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class UserResource {

    private UserDaoService service;

    public UserResource(UserDaoService sevice) {
        this.service = sevice;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user = service.findOne(id);
        if(user == null) throw  new UserNotFoundException("id: " + id);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        service.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() //getting current request /users
                .path("/{id}") // appending /{id} to /users
                .buildAndExpand(savedUser.getId()) // replacing {id} with real id
                .toUri(); //converting to Uri
        return ResponseEntity.created(location).build();
    }
}
