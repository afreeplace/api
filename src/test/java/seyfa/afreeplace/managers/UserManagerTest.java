package seyfa.afreeplace.managers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.request.PasswordRequest;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.entities.request.UserRequest;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserManagerTest {

    @Autowired
    UserManager userManager;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TradeRepository tradeRepository;

    int userId;
    String email = "test@api.fr", password = "password";

    int tradeId1, tradeId2;

    @BeforeEach
    public void before() {
        userId = UserBuilderTest.create(userRepository, email, passwordEncoder.encode(password)).getId();

        tradeId1 = TradeBuilderTest.create(tradeRepository, "Trade1", Trade.Status.VALIDATED).getId();
        tradeId2 = TradeBuilderTest.create(tradeRepository, "Trade2", Trade.Status.VALIDATED).getId();
        assertNotNull(tradeRepository.findById(tradeId1).orElse(null));
        assertNotNull(tradeRepository.findById(tradeId2).orElse(null));
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
        TradeBuilderTest.delete(tradeId1, tradeRepository);
        TradeBuilderTest.delete(tradeId2, tradeRepository);
    }

    @Test
    public void ok () {
        assertNotNull(userManager);
    }

    @Test
    public void findByEmail() {
        assertNotNull(userManager.find(email));
    }

    @Test
    public void findByEmailNull() {
        assertThrows(ManagerException.class, () -> {
            userManager.find("teszrétt@api.fr");
        });
    }

    @Test
    public void createUserEmailTwiceFails() {
        assertThrows(ManagerException.class, () ->  {
            userManager.create(userRepository.findByEmail(email).get());
        });
    }

    @Test
    public void updateUserWorks() {
        String secondFirstname = "Fallou2";
        String secondLastname = "Seye2";

        User user = userRepository.findByEmail(email).get();
        user.setFirstname(secondFirstname);
        user.setLastname(secondLastname);
        userManager.update(user);

        User updatedUser = userRepository.findByEmail(email).get();
        assertEquals(secondFirstname, updatedUser.getFirstname());
        assertEquals(secondLastname , updatedUser.getLastname());
    }

    @Test
    public void changePasswordWorks() {
        String newPassword = "newPassword";

        PasswordRequest passwordRequest = new PasswordRequest(password, newPassword);
        userManager.changePassword(userId, passwordRequest);

        UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken(email, newPassword);
        authenticationManager.authenticate(username);
    }

    @Test
    public void changePasswordFails() {
        String wrongPassword = "fezoghizhpgo";
        String newPassword = "newPassword";

        assertThrows(ManagerException.class, () -> {
            PasswordRequest passwordRequest = new PasswordRequest(wrongPassword, newPassword);
            userManager.changePassword(userId, passwordRequest);
        });

        assertThrows(BadCredentialsException.class, () -> {
            UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken(email, newPassword);
            authenticationManager.authenticate(username);
        });

    }

    @Test
    public void deleteNullUserFails() {
        int nonExistingUserId = 9794674;
        assertThrows(Exception.class, () -> {
           userManager.delete(nonExistingUserId);
        });
    }


}
