package seyfa.afreeplace.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Rate;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.utils.RateBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class RateRepositoryTest {

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
        userId = UserBuilderTest.create(userRepository, "test@apii.fr", "password").getId();
        tradeId = TradeBuilderTest.create(tradeRepository, "name", Trade.Status.VALIDATED).getId();
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
        TradeBuilderTest.delete(tradeId, tradeRepository);
    }

    @Test
    public void ok() {
        assertNotNull(userRepository);
    }

    @Test
    public void create() {

        Rate rate = RateBuilderTest.create(null, 5, "COmment");
        rate.setUser(userRepository.findById(userId).get());
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rateRepository.save(rate);

        assertNotNull(rateRepository.findById(rate.getId()));
        assertNotNull(rateRepository.findByUserAndTrade(userId, tradeId));

    }
}
