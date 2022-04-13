package seyfa.afreeplace.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Rate;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.RateRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.RateBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    int ownerId;
    int tradeId;
    int rateId;

    String userEmail = "user.rate@test.fr";
    String ownerEmail = "owner.rate@test.fr";

    @BeforeEach
    public void before() {
        userId =  UserBuilderTest.create(userRepository, userEmail, "password").getId();
        ownerId = UserBuilderTest.create(userRepository, ownerEmail, "password").getId();
        tradeId = TradeBuilderTest.create(tradeRepository, "name", Trade.Status.VALIDATED).getId();

        Trade trade = tradeRepository.findById(tradeId).get();
        trade.setOwner(userRepository.findById(ownerId).get());
        tradeRepository.save(trade);

    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
        UserBuilderTest.delete(ownerId, userRepository);
        TradeBuilderTest.delete(tradeId, tradeRepository);
    }

    @Test
    public void ok() {
        assertNotNull(rateManager);
    }

    @Test
    public void createRate() {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());

        rate = rateManager.create(rate);
        rateId = rate.getId();

        assertNotNull(rateRepository.findById(rate.getId()));
    }


    @Test
    public void createRateUserNotFound() {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(new User());

        assertThrows(ManagerException.class, () -> {
            rateManager.create(rate);
        });
    }

    @Test
    public void createRateNotFound() {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(new Trade());
        rate.setUser(userRepository.findById(userId).get());

        assertThrows(ManagerException.class, () -> {
            rateManager.create(rate);
        });

    }

    @Test
    public void createRateBadRate() {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());
        rate.setRate(-1);

        assertThrows(ManagerException.class, () -> {
            rateManager.create(rate);
        });

        rate.setRate(7);

        assertThrows(ManagerException.class, () -> {
            rateManager.create(rate);
        });
    }

    @Test
    public void createRateTwiceNotWorking() {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());

        rateManager.create(rate);
        assertNotNull(rateRepository.findById(rate.getId()));

        assertThrows(ManagerException.class, () -> {
            rateManager.create(rate);
        });
    }

    @Test
    public void editRateWorks() throws Exception {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());

        rate = rateManager.create(rate);
        rateId = rate.getId();

        rate = rateRepository.findById(rateId).orElseThrow(() -> new Exception("Pas de rate"));

        rate.setRate(1);
        rate.setComment("Ok");

        rateManager.edit(rate);

        assertEquals(rateRepository.findById(rateId).get().getRate(), 1);
        assertEquals(rateRepository.findById(rateId).get().getComment(), "Ok");
    }

    @Test
    public void editRateNotFound() throws Exception {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());

        rate = rateManager.create(rate);
        rateId = rate.getId();

        rate = rateRepository.findById(rateId).orElseThrow(() -> new Exception("Pas de rate"));

        rate.setRate(1);
        rate.setComment("Ok");
        rate.setId(-10);

        Rate finalRate = rate;
        assertThrows(ManagerException.class, () -> {
            rateManager.edit(finalRate);
        });

    }

    @Test
    public void editRateBadRate() throws Exception {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());

        rate = rateManager.create(rate);
        rateId = rate.getId();

        rate = rateRepository.findById(rateId).orElseThrow(() -> new Exception("Pas de rate"));

        rate.setRate(0);
        rate.setComment("Ok");

        Rate finalRate = rate;
        assertThrows(ManagerException.class, () -> {
            rateManager.edit(finalRate);
        });

        rate.setRate(6);

        Rate finalRate1 = rate;
        assertThrows(ManagerException.class, () -> {
            rateManager.edit(finalRate1);
        });
    }

}
