package seyfa.afreeplace.managers;

import org.apache.tomcat.jni.Local;
import seyfa.afreeplace.entities.PasswordRequest;
import seyfa.afreeplace.entities.User;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserManager implements IManager<Integer, User> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User find(Integer id) throws ManagerException {
        return userRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));
    }

    public User find(String email) throws ManagerException {
        return userRepository.findByEmail(email).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));
    }

    @Override
    public void create(User user) throws ManagerException {
        User exists = userRepository.findByEmail(user.getEmail()).orElse(null);

        if(exists != null) {
            throw new ManagerException(ExceptionConstants.emailAlreadyExists());
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        LocalDateTime time = LocalDateTime.now();

        User userToCreate = new User();
        userToCreate.setFirstname(user.getFirstname());
        userToCreate.setLastname(user.getLastname());
        userToCreate.setEmail(user.getEmail());
        userToCreate.setPassword(encodedPassword);
        userToCreate.setCreationDate(time);

        System.err.println(userToCreate);
        userRepository.save(userToCreate);
    }

    @Override
    public void update(User object) throws ManagerException {
        User user = userRepository.findById(object.getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));

        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());

        userRepository.save(user);
    }

    public void changePassword(int userId, PasswordRequest passwordRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));

        if(!passwordEncoder.matches(passwordRequest.getLastPassword(), user.getPassword())) {
            throw new ManagerException(ExceptionConstants.passwordNotMatchs());
        }

        String newPassword = passwordEncoder.encode(passwordRequest.getNewPassword());

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void delete(Integer id) throws ManagerException {
        // TODO : add business rules
        User user = userRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));
        userRepository.delete(user);
    }

}
