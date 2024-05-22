package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record UserDto(
        @Nullable Long id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        String email)
{

}

record ListUserDto(@Nullable Long id, String firstName, String lastName, LocalDate birthdate) {
}

record SimpleUserDto(@Nullable Long id, String firstName, String lastName) {
}


record UserIdAndEmailDto(@Nullable Long id, String email) {
}

record UserEmailDto(String email) {
}

record UserPatchDto(
        @Nullable String firstName,
        @Nullable String lastName,
        @Nullable @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        @Nullable String email
)
{
}

record UserPutDto(
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        String email
)
{
}
