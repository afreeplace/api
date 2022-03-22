package seyfa.afreeplace.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.utils.TradeBuilderTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TradeRepositoryTest {

    org.junit.platform.commons.logging.Logger Logger = LoggerFactory.getLogger(TradeRepositoryTest.class);

    @Autowired
    TradeRepository tradeRepository;

    int tradeId;

    @BeforeEach
    public void before() {
        tradeId = TradeBuilderTest.create(tradeRepository, "Seyfa Tech", Trade.Status.VALIDATED).getId();
    }

    @AfterEach
    public void after() {
        TradeBuilderTest.delete(tradeId, tradeRepository);
    }

    @Test
    public void ok() {
        assertNotNull(tradeRepository);
    }


}
