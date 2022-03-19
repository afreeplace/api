package seyfa.afreeplace.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.entities.Trade;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TradeRepositoryTest {

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
