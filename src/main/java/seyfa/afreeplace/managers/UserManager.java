package seyfa.afreeplace.managers;

import seyfa.afreeplace.entities.request.PasswordRequest;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserManager implements IManager<User, Integer> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User find(Integer id) throws ManagerException {
        User user = userRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));

        user.getTrades().forEach(trade -> {
            trade.getTags().size();
            trade.getPictures().size();
            trade.getSchedule().size();
        });

        return user;
    }

    public User find(String email) throws ManagerException {
        return userRepository.findByEmail(email).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));
    }

    @Override
    public Integer create(User user) throws ManagerException {
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

        return user.getId();
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
