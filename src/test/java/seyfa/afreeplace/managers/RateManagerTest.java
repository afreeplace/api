package seyfa.afreeplace.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.repositories.RateRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RateManagerTest {

    @Autowired
    RateManager rateManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    RateRepository rateRepository;

    int userId;
    int tradeId;
    int rateId;

    @BeforeEach
    public void before() {
        userId = UserBuilderTest.create(userRepository, "test@api.fr", "password").getId();
        tradeId = TradeBuilderTest.create(tradeRepository, "name", Trade.Status.VALIDATED).getId();
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
        TradeBuilderTest.delete(tradeId, tradeRepository);
    }

    @Test
    public void ok() {
        assertNotNull(rateManager);
    }

    @Test
    public void createRate() {

    }

    @Test
    public void createRateTwiceWorks() {

    }

    @Test
    public void editRateTwice() {

    }

}
