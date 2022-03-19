package seyfa.afreeplace.repositories;

import seyfa.afreeplace.utils.UserBuilderTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    int userId;

    @BeforeEach
    public void before() {
        userId = UserBuilderTest.create(userRepository, "test@api.fr", "password").getId();
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
    }

    @Test
    public void ok() {
        assertNotNull(userRepository);
    }

    @Test
    public void findByEmailWorks() {
        assertNotNull(userRepository.findByEmail("test@api.fr").orElse(null));
    }

}
