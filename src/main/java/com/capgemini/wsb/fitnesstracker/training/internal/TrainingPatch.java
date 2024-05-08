package com.capgemini.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalDouble;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingPatch {
    @Nullable private Long userId;
    @Nullable @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX") private Date startTime;
    @Nullable @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX") private Date endTime;
    @Nullable private ActivityType activityType;
    @Nullable private Double distance;
    @Nullable private Double averageSpeed;
}
