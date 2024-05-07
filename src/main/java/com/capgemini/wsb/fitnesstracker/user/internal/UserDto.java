package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

record UserDto(
        @Nullable Long Id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        String email)
{

}

record UserIdAndNameDto(@Nullable Long Id, String firstName, String lastName) {

}


record UserIdAndEmailDto(@Nullable Long Id, String email) {
}

record UserEmailDto(String email) {
}

record UserPatchDto(
        @Nullable Long Id,
        @Nullable String firstName,
        @Nullable String lastName,
        @Nullable @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        @Nullable String email) {
}
