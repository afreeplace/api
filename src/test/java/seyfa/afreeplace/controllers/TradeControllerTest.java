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
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeControllerTest {

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    TradeController tradeController;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    UserRepository userRepository;

    int tradeId, secondTradeId;
    String name = "Seyfa Tech";
    Trade.Status status = Trade.Status.REQUESTED;

    @BeforeEach
    public void before() {
        tradeId = TradeBuilderTest.create(tradeRepository, name, status).getId();
    }

    @AfterEach
    public void after() {
        TradeBuilderTest.delete(tradeId, tradeRepository);
        TradeBuilderTest.delete(secondTradeId, tradeRepository);
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


}
