package seyfa.afreeplace.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTasks {

    @Scheduled(cron = "0 * * * * *")
    public void testTasks() {
    }

}
