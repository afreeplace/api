package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Hours;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.entities.request.UserRequest;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.*;
import seyfa.afreeplace.services.geocoding.GeocodingService;
import seyfa.afreeplace.utils.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserAccessTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAccess userAccess;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    UserRequest userRequest;

    @Autowired
    CompleteUserBuilderTest completeUserBuilderTest;

    int userId, userId2;
    int tradeId, tradeId2;

    @BeforeEach
    public void before() throws Exception {
        int userId = completeUserBuilderTest.create("fallou@test.fr");
        User foundUser = userRepository.findById(userId).orElse(null);

        // "auth user"
        userRequest.setAuthUser(userRepository.findById(userId).get());
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
        UserBuilderTest.delete(userId2, userRepository);

        TradeBuilderTest.delete(tradeId, tradeRepository);
        TradeBuilderTest.delete(tradeId2, tradeRepository);
    }

    @Test
    public void ok() {
        assertNotNull(userAccess);
    }

    @Test
    public void testUserAccessWprks() {
        userAccess.hasRightToEditTrade(tradeRepository.findById(tradeId).get().getId());
    }

    @Test
    public void testUserAccessRefused() {
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRightToEditTrade(tradeRepository.findById(tradeId2).get().getId());
        });
    }

    @Test
    public void testUserAccessNoUser() {
        User fakeUser = new User();
        fakeUser.setId(-1);
        userRequest.setAuthUser(fakeUser);
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRightToEditTrade(tradeRepository.findById(tradeId2).get().getId());
        });
    }

    @Test
    public void testUserAccessNoTrade() {
        assertThrows(ManagerException.class, () -> {
            userAccess.hasRightToEditTrade(-1);
        });
    }



}
