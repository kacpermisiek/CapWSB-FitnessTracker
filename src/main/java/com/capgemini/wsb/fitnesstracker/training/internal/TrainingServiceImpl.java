package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
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
    public List<Training> getTrainingsFinishedAfter(Date endTime) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getEndTime().after(endTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    @Override
    public Training addTraining(TrainingCreate training) throws IllegalArgumentException{
        User user = userProvider.getUser(training.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Training trainingEntity = new Training(
                user,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
        return trainingRepository.save(trainingEntity);
    }

    @Override
    public void patchTraining(Long trainingId, TrainingPatch trainingUpdateTo) {
        log.info("Updating training with ID {}", trainingId);
        Optional<Training> training = trainingRepository.findById(trainingId);
        if (training.isEmpty()) {
            throw new IllegalArgumentException("Training not found");
        }
        if (trainingUpdateTo.getUserId() != null) {
            User user = userProvider.getUser(trainingUpdateTo.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            training.get().setUser(user);
        }
        if (trainingUpdateTo.getStartTime() != null) {
            training.get().setStartTime(trainingUpdateTo.getStartTime());
        }
        if (trainingUpdateTo.getEndTime() != null) {
            training.get().setEndTime(trainingUpdateTo.getEndTime());
        }
        if (trainingUpdateTo.getActivityType() != null) {
            training.get().setActivityType(trainingUpdateTo.getActivityType());
        }
        if (trainingUpdateTo.getDistance() != null) {
            training.get().setDistance(trainingUpdateTo.getDistance());
        }
        if (trainingUpdateTo.getAverageSpeed() != null) {
            training.get().setAverageSpeed(trainingUpdateTo.getAverageSpeed());
        }
        trainingRepository.save(training.get());

    }

    @Override
    public Training updateTraining(Long trainingId, TrainingPut trainingUpdateTo) {
        log.info("Updating training with ID {}", trainingId);
        log.info("Num of trainings: {}", trainingRepository.count());
        log.info("Num of users: {}", userProvider.getNumOfUsers());
        Optional<Training> training = trainingRepository.findById(trainingId);
        System.out.println(training);
        if (training.isEmpty()) {
            throw new IllegalArgumentException("Training not found");
        }
        User user = userProvider.getUser(trainingUpdateTo.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        training.get().setUser(user);
        training.get().setStartTime(trainingUpdateTo.getStartTime());
        training.get().setEndTime(trainingUpdateTo.getEndTime());
        training.get().setActivityType(trainingUpdateTo.getActivityType());
        training.get().setDistance(trainingUpdateTo.getDistance());
        training.get().setAverageSpeed(trainingUpdateTo.getAverageSpeed());
        return trainingRepository.save(training.get());
    }

    @Override
    public List<Training> findAllForUser(Long id) {
        return trainingRepository.findByUserId(id);
    }
}
