package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/v1/training")
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

    @GetMapping("/finishedAt/{dateTo}")
    public ResponseEntity<List<TrainingTo>> getTrainingsFinishedAt(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        try {
            List<TrainingTo> trainings = trainingService.getTrainingsFinishedAt(dateTo).stream().map(trainingMapper::toTrainingTo).collect(toList());
            return ResponseEntity.ok(trainings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/activityType/{activityType}")
    public ResponseEntity<List<TrainingTo>> getTrainingsByActivityType(@PathVariable ActivityType activityType) {
        try {
            List<TrainingTo> trainings = trainingService.getTrainingsByActivityType(activityType).stream().map(trainingMapper::toTrainingTo).collect(toList());
            return ResponseEntity.ok(trainings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<TrainingTo> addTraining(@RequestBody TrainingCreate trainingCreateTo) {
        TrainingTo training = new TrainingTo();

        training.setUser()

        Training training = trainingService.addTraining(trainingMapper.toTraining(trainingTo));
    }
}
