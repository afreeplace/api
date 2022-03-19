package seyfa.afreeplace.managers;

import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.UserBuilderTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertThrows;

@SpringBootTest
public class AuthenticationManagerTest {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    int userId;
    String email = "test@api.fr", password = "password";

    @BeforeEach
    public void before() {
        userId = UserBuilderTest.create(userRepository, email, passwordEncoder.encode(password)).getId();
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
    }

    @Test
    public void testAuthenticationWorks() {
        UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(username);
    }

    @Test
    public void testAuthenticationFailedWitEmail() {
        UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken("test@errror.fr", password);

        assertThrows(InternalAuthenticationServiceException.class, () -> {
            authenticationManager.authenticate(username);
        });
    }

    @Test
    public void testAuthenticationFailedWitPassword() {
        UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken(email, "zfgzrgerg");

        assertThrows(BadCredentialsException.class, () -> {
            authenticationManager.authenticate(username);
        });
    }

}
