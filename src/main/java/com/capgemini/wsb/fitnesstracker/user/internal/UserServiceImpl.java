package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) throws IllegalArgumentException{
        log.info("Deleting User with ID {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> getUser(final Long userId) throws IllegalArgumentException{
        try{
            return userRepository.findById(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public List<User> getUserByEmail(final String email) {
        return userRepository.findByEmailContains(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> finUsersOlderThan(LocalDate olderThanDate) {
        return userRepository.findOlderThan(olderThanDate);
    }

    public void patchUser(Long userId,  UserPatchDto userPatch) throws IllegalArgumentException{
        log.info("Patching User with ID {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        if (userPatch.firstName() != null) {
            user.get().setFirstName(userPatch.firstName());
        }
        if (userPatch.lastName() != null) {
            user.get().setLastName(userPatch.lastName());
        }
        if (userPatch.birthdate() != null) {
            user.get().setBirthdate(userPatch.birthdate());
        }
        if (userPatch.email() != null) {
            user.get().setEmail(userPatch.email());
        }
        userRepository.save(user.get());
    }

    public void putUser(Long userId, UserPutDto putDto) throws IllegalArgumentException{
        log.info("Updating User with ID {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        user.get().setFirstName(putDto.firstName());
        user.get().setLastName(putDto.lastName());
        user.get().setBirthdate(putDto.birthdate());
        user.get().setEmail(putDto.email());
        userRepository.save(user.get());
    }

}