package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller for managing users.
 * <p>
 *     This controller provides endpoints for managing users.
 *     It allows to get all users, get a single user by ID and add a new user.
 *     The controller uses {@link UserServiceImpl} to perform operations on users.
 *     The controller uses {@link UserMapper} to map between {@link User} and {@link UserDto}.
 *     The controller is available under /v1/users path.
 *     </p>
 * @author Kacper Misiek
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserIdAndNameDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toIdAndNameDto)
                          .toList();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId)
                          .map(userMapper::toDto)
                          .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(userService.createUser(userMapper.toEntity(userDto)), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public List<UserIdAndEmailDto> getUsersByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                          .stream()
                          .map(userMapper::toIdAndEmailDto)
                          .toList();
    }

    @GetMapping("/olderThan/{age}")
    public List<UserDto> getUsersOlderThan(@PathVariable int age) {
        return userService.finUsersOlderThan(age)
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }
}