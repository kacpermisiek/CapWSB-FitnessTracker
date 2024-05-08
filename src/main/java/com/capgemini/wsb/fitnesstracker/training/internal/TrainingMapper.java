package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserMapper userMapper;
    TrainingTo toTrainingTo(Training training){
        return new TrainingTo(
                training.getId(),
                userMapper.toDto(training.getUser()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

//    public Training toTraining(TrainingTo trainingTo) {
//        return new Training(
//                userMapper.toEntity(trainingTo.getUser()),
//                trainingTo.getStartTime(),
//                trainingTo.getEndTime(),
//                trainingTo.getActivityType(),
//                trainingTo.getDistance(),
//                trainingTo.getAverageSpeed()
//        );
//    }
}
