package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @PostMapping
    public User addUser(@RequestBody UserDto userDto) {
            return userService.createUser(userMapper.toEntity(userDto)).orElseThrow(() -> new IllegalArgumentException("User already exists"));
    }
}