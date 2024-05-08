package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Retrieves all trainings for a specific user.
     *
     * @param userId id of the user
     * @return A list of all trainings for the user
     */
    List<Training> findByUserId(Long userId);


    /**
     * Retrieves all trainings for a specific activity type.
     *
     * @param activityType type of the activity
     * @return A list of all trainings for the activity type
     */
    List<Training> findByActivityType(ActivityType activityType);
}
