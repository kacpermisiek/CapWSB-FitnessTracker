package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingTo;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<User> getTraining(Long trainingId);

    /**
     * Retrieves all trainings.
     *
     * @return A list of all trainings
     */
    List<Training> getAllTrainings();

    /**
     * Retrieves all trainings for a specific user.
     *
     * @param userId id of the user
     * @return A list of all trainings for the user
     */
    List<Training> getUserTrainings(Long userId);


    /**
     * Retrieves all trainings that finished at a specific time.
     *
     * @param endTime time when the training finished
     * @return A list of all trainings that finished at the specified time
     */
    List<Training> getTrainingsFinishedAt(Date endTime);

    List<Training> getTrainingsByActivityType(ActivityType activityType);

    Training addTraining(Training training);
}
