package seyfa.afreeplace.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.entities.business.ScheduleDay;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.repositories.DayRepository;
import seyfa.afreeplace.repositories.UserRepository;

import java.time.DayOfWeek;

public class DayBuilderTest {

    static final Logger logger = LoggerFactory.getLogger(UserBuilderTest.class);

    public static ScheduleDay create(DayRepository dayRepository) {
        ScheduleDay scheduleDay = new ScheduleDay();
        scheduleDay.setDayOfWeek(DayOfWeek.MONDAY);
        scheduleDay.setOpen(false);

        dayRepository.save(scheduleDay);
        return scheduleDay;
    }

    public static void delete(int userId, UserRepository userRepository) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            userRepository.delete(userRepository.findById(userId).get());
            logger.info("Deleted user {}", userId);
        } else {
            logger.info("Already deleted user {}", userId);
        }
    }


}
