package seyfa.afreeplace.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.repositories.UserRepository;

import java.time.LocalDateTime;

public class UserBuilderTest {

    static final Logger logger = LoggerFactory.getLogger(UserBuilderTest.class);

    public static User create(UserRepository userRepository, String email, String password) {
        User user = new User(email, password, LocalDateTime.now());
        user.setFirstname("Fallou");
        user.setLastname("Seye");

        if(userRepository != null) {
            userRepository.save(user);
            logger.info("Created user " + user);
        }
        return user;
    }

    public static void delete(int userId, UserRepository userRepository) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            userRepository.delete(user);
            logger.info("Deleted user {}", userId);
        } else {
            logger.info("Already deleted user {}", userId);
        }
    }

}
