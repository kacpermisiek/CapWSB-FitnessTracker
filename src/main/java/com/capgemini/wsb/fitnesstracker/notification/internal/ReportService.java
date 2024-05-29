package com.capgemini.wsb.fitnesstracker.notification.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Service
@Data
@Slf4j
public class ReportService {
    private static final String ReportTitle = "Fitness Tracker Report";
    private final JavaMailSender javaMailSender;
    private final UserProvider userProvider;
    private final TrainingProvider trainingProvider;

    // Emails being sent every Sunday at midnight
    @Scheduled(cron = "0 0 0 ? * SUN")
    public void sendEmailReports() {
        log.info("Creating report");
        final List<User> users = userProvider.findAllUsers();
        users.forEach(user -> {
            final SimpleMailMessage simpleMailMessage = createEmail(user.getEmail(), trainingProvider.findAllForUser(user.getId()));
            javaMailSender.send(simpleMailMessage);
        });

        log.info("Report created");
    }

    private SimpleMailMessage createEmail(String customerEmail, List<Training> trainings){
        Date startOfLastWeek = getStartOfLastWeek();
        Date yesterday = getYesterday();
        List<Training> lastWeekTrainings = trainings.stream().filter(
                training -> training.getStartTime().after(startOfLastWeek) && training.getStartTime().before(yesterday)
        ).toList();

        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(customerEmail);
        email.setSubject(ReportTitle);
        email.setText(getEmailText(lastWeekTrainings));
        return email;
    }

    private String getEmailText(List<Training> lastWeekTrainings) {
        final StringBuilder builder = new StringBuilder("""
                Hello,
                You had %s trainings last week,
                Below you can find detailed rundown of your trainings last week:
                ----
                """.formatted(lastWeekTrainings.size()));
        lastWeekTrainings.forEach(training -> {
            builder.append("""
                %s - %s
                Training type: %s
                distance: %s
                average speed: %s
                
                ----
                """.formatted(training.getStartTime(),
                    training.getEndTime() == null ? "-" : training.getEndTime(),
                    training.getActivityType(),
                    training.getDistance(),
                    training.getAverageSpeed()
            ));
        });
        log.info(builder.toString());
        return builder.toString();
    }

    private Date getStartOfLastWeek() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        return calendar.getTime();
    }

    private Date getYesterday() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }
}
