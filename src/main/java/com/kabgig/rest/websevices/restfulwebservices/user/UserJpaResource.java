package com.kabgig.rest.websevices.restfulwebservices.user;

import com.kabgig.rest.websevices.restfulwebservices.jpa.PostRepository;
import com.kabgig.rest.websevices.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw  new UserNotFoundException("id: " + id);
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw  new UserNotFoundException("id: " + id);
        return user.get().getPosts();
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() //getting current request /users
                .path("/{id}") // appending /{id} to /users
                .buildAndExpand(savedUser.getId()) // replacing {id} with real id
                .toUri(); //converting to Uri
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw  new UserNotFoundException("id: " + id);
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() //getting current request /users
                .path("/{id}") // appending /{id} to /users
                .buildAndExpand(savedPost.getId()) // replacing {id} with real id
                .toUri(); //converting to Uri
        return ResponseEntity.created(location).build();

    }

    @GetMapping("/jpa/users/{userId}/posts/{postId}")
    public Post retrievePostForUser(@PathVariable int userId, @PathVariable int postId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw  new UserNotFoundException("id: " + userId);
        return user.get()
                .getPosts()
                .stream()
                .filter(post -> postId == post.getId())
                .findFirst()
                .get();
    }
}
