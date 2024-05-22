package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    /**
     * Query searching users by email address. It matches by partial match.
     *
     * @param email email of the user to search
     * @return {@link List} of users that match the query
     */
    default List<User> findByEmailContains(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .collect(Collectors.toList());

    }

    /**
     * Query searching users older than given age.
     *
     * @param age The age of the user to be searched
     * @return List of users older than given age
     */
    default List<User> findOlderThan(int age) {
        LocalDate cutOffDate = LocalDate.now().minusYears(age);
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(cutOffDate))
                .collect(Collectors.toList());
    }
}
