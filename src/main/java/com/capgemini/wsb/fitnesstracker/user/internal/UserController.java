package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUser(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return new ResponseEntity<>(userMapper.toDto(user.get()), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        try {
            User newUser = userService.createUser(userMapper.toEntity(userDto));
            return new ResponseEntity<>(userMapper.toDto(newUser), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (Exception e) {
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

    @GetMapping("/older/{age}")
    public List<UserDto> getUsersOlderThan(@PathVariable int age) {
        return userService.finUsersOlderThan(age)
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> patchUser(@PathVariable Long userId, @RequestBody UserPatchDto userPatchDto) {
        try {
            userService.patchUser(userId, userPatchDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}