package seyfa.afreeplace.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.repositories.DayRepository;
import seyfa.afreeplace.repositories.UserRepository;

public class DayBuilderTest {

    static final Logger logger = LoggerFactory.getLogger(UserBuilderTest.class);

    public static User create(DayRepository dayRepository) {
//        Availability availability = new Availability();
//        availability.setMonday(false);
//        availability.setFriday(true);
//
//        if(userRepository != null) {
//            userRepository.save(user);
//            logger.info("Created user " + user);
//        }
//        return user;
        return null;
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
