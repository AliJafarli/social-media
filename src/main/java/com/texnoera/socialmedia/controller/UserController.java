package com.texnoera.socialmedia.controller;

import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.request.UserUpdateRequest;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import com.texnoera.socialmedia.service.abstracts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Received request to get all users");
        List<UserResponse> users = userService.getAll();
        log.info("Successfully retrieved {} users", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        log.info("Received request to get user with ID: {}", id);
        UserResponse userResponse = userService.getResponseById(id);
        log.info("Successfully retrieved user with ID: {}", id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/is-following")
    public ResponseEntity<Boolean> isFollowing(@RequestParam Integer userId, @RequestParam Integer followingId) {
        log.info("Received request to check if user with ID: {} is following user with ID: {}", userId, followingId);
        Boolean isFollowing = userService.isFollowing(userId, followingId);
        log.info("User with ID: {} is following user with ID: {}: {}", userId, followingId, isFollowing);
        return new ResponseEntity<>(isFollowing, HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserAddRequest userAddRequest) {
        log.info("Received request to add user: {}", userAddRequest.getEmail());
        UserResponse userResponse = userService.add(userAddRequest);
        log.info("Successfully created user with ID: {}", userResponse.getId());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        log.info("Received request to update user with ID: {}: {}", id, userUpdateRequest);
        UserResponse userResponse = userService.update(id, userUpdateRequest);
        log.info("Successfully updated user with ID: {}: {}", id, userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Received request to delete user with ID: {}", id);
        userService.delete(id);
        log.info("Successfully deleted user with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
