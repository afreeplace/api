package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import seyfa.afreeplace.entities.PasswordRequest;
import seyfa.afreeplace.entities.User;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.UserBuilderTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountControllerTest {

    @Autowired
    AccountController accountController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    String email = "test@pi.fr", password = "password";

    int userId, nullUserId, createdUserId;

    @BeforeEach
    public void before() {
        userId = UserBuilderTest.create(userRepository, email, passwordEncoder.encode(password)).getId();
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
        UserBuilderTest.delete(createdUserId, userRepository);
    }

    @Test
    public void ok() {
        assertNotNull(accountController);
    }

    @Test
    public void testCreateWorks() {
        User createdUser = UserBuilderTest.create(null, "test5@api2.fr", passwordEncoder.encode(password));
        createdUserId = createdUser.getId();

        BindingResult result = new BeanPropertyBindingResult(createdUser, "request");
        ResponseEntity responseEntity = accountController.createAccuont(createdUser, result);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        userRepository.delete(createdUser);
        assertNull(userRepository.findById(createdUserId).orElse(null));
    }

    @Test
    public void testUpdateWorks() {
        User user = userRepository.findById(userId).get();

        String newEmail = "test@update";
        String fname2 = "Fallou2";
        String name2 = "Seye3";

        user.setFirstname(fname2);
        user.setLastname(name2);
        user.setEmail(newEmail);

        BindingResult result = new BeanPropertyBindingResult(user, "request");
        ResponseEntity responseEntity = accountController.updateAccuont(user, result);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        User updatedUser = userRepository.findById(userId).get();
        assertEquals   (fname2   , updatedUser.getFirstname());
        assertEquals   (name2    , updatedUser.getLastname());
        assertNotEquals(newEmail , updatedUser.getEmail());
    }

    @Test
    public void testCUpdateNullFails() {
        User user = new User();
        user.setId(nullUserId);

        BindingResult result = new BeanPropertyBindingResult(user, "request");

        assertThrows(ManagerException.class, () -> {
            ResponseEntity responseEntity = accountController.updateAccuont(user, result);
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        });
    }

    @Test
    public void testUpdateCredentialsWorks() {
        String newPassword = "password2";
        PasswordRequest passwordRequest = new PasswordRequest(password, newPassword);

        BindingResult result = new BeanPropertyBindingResult(passwordRequest, "request");
        ResponseEntity responseEntity = accountController.updateCredentials(userId, passwordRequest, result);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User updatedUser = userRepository.findById(userId).get();
        assertTrue(passwordEncoder.matches(newPassword, updatedUser.getPassword()));
    }

    @Test
    public void testCUpdateCredentialsNullFails() {
        String newPassword = "password2";
        PasswordRequest passwordRequest = new PasswordRequest("sgpjrhjpspĵ", newPassword);

        BindingResult result = new BeanPropertyBindingResult(passwordRequest, "request");

        assertThrows(ManagerException.class, () -> {
            ResponseEntity responseEntity = accountController.updateCredentials(userId, passwordRequest, result);
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        });

        User updatedUser = userRepository.findById(userId).get();
        assertTrue(passwordEncoder.matches(password, updatedUser.getPassword()));
    }

    @Test
    public void testDeleteWorks() {
        accountController.deleteUser(userId);
        assertThrows(ManagerException.class, () -> {
            userRepository.findById(userId).orElseThrow(() -> new ManagerException(""));
        });
    }


    @Test
    public void testDeleteFails() {
        assertThrows(ManagerException.class, () -> {
            accountController.deleteUser(nullUserId);
        });
    }

}
