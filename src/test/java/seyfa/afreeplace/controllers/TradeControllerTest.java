package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeControllerTest {

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    TradeController tradeController;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

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
    public void testCreateWorks() {
        User owner = UserBuilderTest.create(userRepository, "Fallou@test.fr", "Seye");
        logger.info("Owner created {}", owner);
        Trade trade = TradeBuilderTest.create(null, "SeyfaTech3", Trade.Status.VALIDATED);
        trade.setOwner(owner);

        BindingResult result = new BeanPropertyBindingResult(trade, "request");
        ResponseEntity responseEntity = tradeController.createTrade(trade, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // clean up
        UserBuilderTest.delete(owner.getId(), userRepository);
    }

    @Test
    public void testCreateFails() {
        Trade trade = TradeBuilderTest.create(null, "SeyfaTech3", Trade.Status.VALIDATED);

        assertThrows (ManagerException.class, () -> {
            BindingResult result = new BeanPropertyBindingResult(trade, "request");
            ResponseEntity responseEntity = tradeController.createTrade(trade, result);
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        });
    }

    @Test
    public void testUpdateWorks() {
        Trade trade = tradeRepository.findById(tradeId).get();

        String newName        = "SeyfaTech2";
        String newPhoneNumber = "phone2";
        String website     = "zegpjozrgjop";

        trade.setName(newName);
        trade.setPhoneNumber(newPhoneNumber);
        trade.setWebsiteUrl(website);

        BindingResult result = new BeanPropertyBindingResult(trade, "request");
        ResponseEntity responseEntity = tradeController.updateTrade(trade, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Trade updatedTrade = tradeRepository.findById(tradeId).get();
        assertEquals(newName, updatedTrade.getName());
        assertEquals(newPhoneNumber, updatedTrade.getPhoneNumber());
        assertEquals(website, updatedTrade.getWebsiteUrl());
        assertNull(trade.getLogoUrl());
    }

    @Test
    public void testUpdateFails() {
        Trade trade = tradeRepository.findById(tradeId).get();

        String newName        = "SeyfaTech2";
        String newPhoneNumber = "phone2";
        String newLogoUrl     = "zegpjozrgjop";

        trade.setName(newName);
        trade.setPhoneNumber(newPhoneNumber);
        trade.setLogoUrl(newLogoUrl);
        trade.setId(-1);

        assertThrows(ManagerException.class, () -> {
            BindingResult result = new BeanPropertyBindingResult(trade, "request");
            ResponseEntity responseEntity = tradeController.updateTrade(trade, result);
            tradeController.updateTrade(trade, result);
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        });
    }

    @Test
    public void testDeleteWorks() {
        ResponseEntity responseEntity = tradeController.deleteTrade(tradeId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Transactional
    @Test
    public void addTagToTrade() {
        User owner = UserBuilderTest.create(userRepository, "Fallou@test.fr", "Seye");

        Trade trade = TradeBuilderTest.create(tradeRepository, "SeyfaTech3", Trade.Status.VALIDATED);
        trade.setOwner(owner);

        // add tag to trade
        ResponseEntity<Map<String, Object>> response = tradeController.addTag(tradeId, tagId1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEntity<Map<String, Object>> response3 = tradeController.addTag(tradeId, tagId2);
        assertEquals(HttpStatus.OK, response3.getStatusCode());

        assertEquals(2, tradeRepository.findById(tradeId).orElse(null).getTags().size());

        // remove tafs
        ResponseEntity<Map<String, Object>> response4 = tradeController.removeTag(tradeId, tagId1);
        assertEquals(HttpStatus.OK, response4.getStatusCode());
        assertEquals(1, tradeRepository.findById(tradeId).orElse(null).getTags().size());

        ResponseEntity<Map<String, Object>> response5 = tradeController.removeTag(tradeId, tagId2);
        assertEquals(HttpStatus.OK, response5.getStatusCode());
        assertEquals(0, tradeRepository.findById(tradeId).orElse(null).getTags().size());

        assertNotNull(tagRepository.findById(tagId1).orElse(null));
        assertNotNull(tagRepository.findById(tagId2).orElse(null));
    }

}
