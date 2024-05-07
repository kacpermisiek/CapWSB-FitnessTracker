package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getUserTrainings(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    @Override
    public List<Training> getTrainingsFinishedAt(Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return trainingRepository.findAll().stream()
                .filter(training -> sdf.format(training.getEndTime()).equals(sdf.format(endTime)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    @Override
    public Training addTraining(Training training) throws IllegalArgumentException{
        return trainingRepository.save(training);
    }
}
