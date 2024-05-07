package com.capgemini.wsb.fitnesstracker.user.api;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new {@link User} entity.
     *
     * @param user the {@link User} entity to be created
     * @return the created {@link User} entity
     * @param user
     * @return User
     */
    User createUser(User user);

    /**
     * Deletes a {@link User} entity.
     *
     * @param userId the ID of the {@link User} entity to be deleted
     * @param userId
     */
    void deleteUser(Long userId);
}
