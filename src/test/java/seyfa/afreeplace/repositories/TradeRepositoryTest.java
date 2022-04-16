package seyfa.afreeplace.repositories;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;
import seyfa.afreeplace.entities.business.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    int ownerId;
    int tradeId;

    @BeforeEach
    public void before() {
        tradeId = TradeBuilderTest.create(tradeRepository, "Seyfa Tech", Trade.Status.VALIDATED).getId();
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(ownerId, userRepository);
        TradeBuilderTest.delete(tradeId, tradeRepository);
    }

    @Test
    public void ok() {
        assertNotNull(tradeRepository);
    }

    @Test
    @Transactional
    public void testFindTradeByOwnerId() {
//        User user = UserBuilderTest.create(userRepository, "fallou@test.fr", "password");
//        ownerId = user.getId();
//        user.getTrades().add(tradeRepository.findById(tradeId).get());
//        assertEquals(1, userRepository.findById(ownerId).get().getTrades().size());
//
//        int trade2Id = TradeBuilderTest.create(tradeRepository, "name", Trade.Status.VALIDATED).getId();
//
//        Trade notNullTrade = tradeRepository.findTradeByOwnerId(ownerId, tradeId).orElse(null);
//        assertNotNull(notNullTrade);
//
//        Trade NullTrade = tradeRepository.findTradeByOwnerId(ownerId, trade2Id).orElse(null);
//        assertNull(NullTrade);
    }

}
