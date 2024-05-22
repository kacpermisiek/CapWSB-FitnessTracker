package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingCreate;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingPatch;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingPut;
import com.capgemini.wsb.fitnesstracker.user.api.User;

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
    Optional<Training> getTraining(Long trainingId);

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
     * Retrieves all trainings that finished after a specific time.
     *
     * @param endTime time when the training finished
     * @return A list of all trainings that finished after the specified time
     */
    List<Training> getTrainingsFinishedAfter(Date endTime);

    /**
     * Retrieves all trainings of a specific activity type.
     *
     * @param activityType type of the activity
     * @return A list of all trainings of the specified activity type
     */
    List<Training> getTrainingsByActivityType(ActivityType activityType);

    /**
     * Adds a new training.
     *
     * @param training training to be added
     * @return The added training
     */
    Training addTraining(TrainingCreate training);

    /**
     * Updates a training.
     *
     * @param trainingId id of the training to be updated
     * @param trainingUpdateTo updated training
     */
    void patchTraining(Long trainingId, TrainingPatch trainingUpdateTo);

    Training updateTraining(Long trainingId, TrainingPut trainingUpdateTo);
}
