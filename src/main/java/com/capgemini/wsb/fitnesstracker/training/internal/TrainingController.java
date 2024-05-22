package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * Controller for training management.
 * <p>
 *     Provides endpoints for managing trainings.
 *     <ul>
 *         <li>GET /v1/training - get all trainings</li>
 *         <li>GET /v1/training/{userId} - get all trainings for user</li>
 *         <li>GET /v1/training/finishedAt/{dateTo} - get all trainings finished at date</li>
 *         <li>GET /v1/training/activityType/{activityType} - get all trainings by activity type</li>
 *         <li>POST /v1/training - add training</li>
 *         <li>PATCH /v1/training/{trainingId} - update training</li>
 *         <li>PUT /v1/training/{trainingId} - update training</li>
 *     </ul>
 * </p>
 * @see TrainingServiceImpl
 * @author Kacper Misiek
 *
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingTo> getAllTrainings() {
        return trainingService.getAllTrainings().stream().map(trainingMapper::toTrainingTo).collect(toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingTo>> getUserTrainings(@PathVariable Long userId) {
        try{
            List<TrainingTo> trainings = trainingService.getUserTrainings(userId).stream().map(trainingMapper::toTrainingTo).toList();
            return ResponseEntity.ok(trainings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/finished/{dateTo}")
    public ResponseEntity<List<TrainingTo>> getTrainingsFinishedAt(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        try {
            List<TrainingTo> trainings = trainingService.getTrainingsFinishedAfter(dateTo).stream().map(trainingMapper::toTrainingTo).collect(toList());
            return ResponseEntity.ok(trainings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingTo>> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        try {
            List<TrainingTo> trainings = trainingService.getTrainingsByActivityType(activityType).stream().map(trainingMapper::toTrainingTo).collect(toList());
            return ResponseEntity.ok(trainings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<TrainingTo> addTraining(@RequestBody TrainingCreate trainingCreateTo) {
        try {
            Training training = trainingService.addTraining(trainingCreateTo);
            return new ResponseEntity<>(trainingMapper.toTrainingTo(training), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong data format");
        }
    }

    @PatchMapping("/{trainingId}")
    public ResponseEntity<TrainingTo> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingPatch trainingUpdateTo) {
        try {
            trainingService.patchTraining(trainingId, trainingUpdateTo);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Training or user not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong data format");
        }
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingTo> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingPut trainingUpdateTo) {
        try {
            Training training = trainingService.updateTraining(trainingId, trainingUpdateTo);
            return new ResponseEntity<>(trainingMapper.toTrainingTo(training), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong data format");
        }
    }
}
