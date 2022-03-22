package seyfa.afreeplace.controllers;

import seyfa.afreeplace.entities.request.AuthRequest;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.UserBuilderTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
public class AuthenticationControllerTest {

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    AuthRequest authRequest = new AuthRequest("test@api.fr", "password");
    int userId;

    @BeforeEach
    public void before() {
        userId = UserBuilderTest.create(userRepository, authRequest.getEmail(), passwordEncoder.encode(authRequest.getPassword())).getId();
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
    }

    @Test
    public void ok() {
        assertNotNull(authenticationController);
    }

    @Test
    public void testAuthenticationWorks() {
        BindingResult result = new BeanPropertyBindingResult(authRequest, "request");

        ResponseEntity<Map<String, Object>> response = authenticationController.authenticate(authRequest, result);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAuthenticationFailedBadCredentialsEmail() {
        authRequest.setEmail("efzghrehterzghoe");
        BindingResult result = new BeanPropertyBindingResult(authRequest, "request");

        assertThrows(InternalAuthenticationServiceException.class, () -> {
            ResponseEntity<Map<String, Object>> response = authenticationController.authenticate(authRequest, result);
        });
//        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testAuthenticationFailedBadCredentialsPassword() {
        authRequest.setPassword("efzghrzghoe");
        BindingResult result = new BeanPropertyBindingResult(authRequest, "request");

        assertThrows(BadCredentialsException.class, () -> {
            ResponseEntity<Map<String, Object>> response = authenticationController.authenticate(authRequest, result);
        });
//        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }



}
