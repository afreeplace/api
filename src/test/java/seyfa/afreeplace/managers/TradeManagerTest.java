package seyfa.afreeplace.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.Application;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.TagRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.TagBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeManagerTest {

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    TradeManager tradeManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    TagRepository tagRepository;

    int tradeId, secondTradeId, userId;
    int tagId1, tagId2;
    String name = "Seyfa Tech";
    Trade.Status status = Trade.Status.REQUESTED;

    @BeforeEach
    public void before() {
        tradeId = TradeBuilderTest.create(tradeRepository, name, status).getId();
        tagId1 = TagBuilderTest.create(tagRepository, "Tag1").getId();
        tagId2 = TagBuilderTest.create(tagRepository, "Tag2").getId();
    }

    @AfterEach
    public void after() {
        TradeBuilderTest.delete(tradeId, tradeRepository);
        TradeBuilderTest.delete(secondTradeId, tradeRepository);
        UserBuilderTest.delete(userId, userRepository);

        TagBuilderTest.delete(tagId1, tagRepository);
        TagBuilderTest.delete(tagId2, tagRepository);
    }

    @Test
    public void ok() {
        assertNotNull(tradeManager);
    }

    @Test
    public void createWithNoOwnerFails() {
        Trade trade = TradeBuilderTest.create(null, "SeyfaTech3", Trade.Status.VALIDATED);
        assertThrows(ManagerException.class, () -> {
            tradeManager.create(trade);
        });
    }

    @Test
    public void createWorks() {
        User owner = UserBuilderTest.create(userRepository, "Fallou@test.fr", "Seye");

        Trade trade = TradeBuilderTest.create(null, "SeyfaTech3", Trade.Status.VALIDATED);
        trade.setOwner(owner);

        secondTradeId = tradeManager.create(trade);
        logger.info("Trade {} ", secondTradeId);
        assertNotNull(tradeRepository.findById(secondTradeId).orElse(null));

        UserBuilderTest.delete(owner.getId(), userRepository);
    }

    @Test
    public void updateWorks() {
        Trade trade = tradeRepository.findById(tradeId).get();

        String newName        = "SeyfaTech2";
        String newPhoneNumber = "phone2";
        String newLogoUrl     = "zegpjozrgjop";

        trade.setName(newName);
        trade.setPhoneNumber(newPhoneNumber);
        trade.setLogoUrl(newLogoUrl);
        tradeManager.update(trade);

        Trade updatedTrade = tradeRepository.findById(tradeId).get();
        assertEquals(newName, updatedTrade.getName());
        assertEquals(newPhoneNumber, updatedTrade.getPhoneNumber());
        assertEquals(newLogoUrl, updatedTrade.getLogoUrl());
        assertNull(trade.getWebsiteUrl());

    }

    @Test
    public void updateNullTradeFails() {
        Trade trade = tradeRepository.findById(tradeId).get();

        String newName        = "SeyfaTech2";
        String newPhoneNumber = "phone2";
        String newLogoUrl     = "zegpjozrgjop";

        trade.setName(newName);
        trade.setPhoneNumber(newPhoneNumber);
        trade.setLogoUrl(newLogoUrl);
        trade.setId(-1);

        assertThrows(ManagerException.class, () -> {
            tradeManager.update(trade);
        });
    }

    @Test
    public void deleteWorks() {
        tradeManager.delete(tradeId);
        assertNull(tradeRepository.findById(tradeId).orElse(null));
    }

    @Transactional
    @Test
    public void addTagToTrade() {
        User owner = UserBuilderTest.create(userRepository, "Fallou@test.fr", "Seye");

        Trade trade = TradeBuilderTest.create(tradeRepository, "SeyfaTech3", Trade.Status.VALIDATED);
        trade.setOwner(owner);

        tradeManager.addTag(tradeId, tagId1);
        tradeManager.addTag(tradeId, tagId2);
        assertEquals(2, tradeRepository.findById(tradeId).orElse(null).getTags().size());

        tradeManager.removeTag(tradeId, tagId1);
        assertEquals(1, tradeRepository.findById(tradeId).orElse(null).getTags().size());

        tradeManager.removeTag(tradeId, tagId2);
        assertEquals(0, tradeRepository.findById(tradeId).orElse(null).getTags().size());

        assertNotNull(tagRepository.findById(tagId1).orElse(null));
        assertNotNull(tagRepository.findById(tagId2).orElse(null));
    }

}
