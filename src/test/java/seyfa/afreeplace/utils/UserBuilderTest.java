package seyfa.afreeplace.utils;

import seyfa.afreeplace.entities.User;
import seyfa.afreeplace.repositories.UserRepository;

import java.time.LocalDateTime;

public class UserBuilderTest {

    public static User create(UserRepository userRepository, String email, String password) {
        User user = new User(email, password, LocalDateTime.now());
        user.setFirstname("Fallou");
        user.setLastname("Seye");

        if(userRepository != null) {
            userRepository.save(user);
            System.err.println("Created user " + user);
        }
        return user;
    }

    public static void delete(int userId, UserRepository userRepository) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            userRepository.delete(userRepository.findById(userId).get());
            System.err.println("Deleted user " + userId);
        } else {
            System.err.println("Deleted user " + userId);
        }
    }

}
