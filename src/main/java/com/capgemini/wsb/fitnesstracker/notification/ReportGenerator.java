package com.capgemini.wsb.fitnesstracker.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@AllArgsConstructor
@Slf4j
public class ReportGenerator {

//    JavaMailSender javaMailSender;
//
//    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
//    public void sendReport() {
//        log.info("Sending report");
//        javaMailSender.send();
//    }


    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void writeSmthToStdOut() {
        log.info("Report processing");
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void logUserTrainingFromLastWeek() {
        log.info("User training from last week");

    }
}
