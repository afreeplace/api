package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import seyfa.afreeplace.entities.business.Rate;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.entities.request.UserRequest;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.RateRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.RateBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RateControllerTest {

    @Autowired
    RateController rateController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    UserRequest userRequest;

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

        userRequest.setAuthUser(userRepository.findById(ownerId).get());
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
        assertNotNull(rateController);
    }

    @Test
    public void createRate() {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());

        BindingResult result = new BeanPropertyBindingResult(rate, "request");
        ResponseEntity responseEntity = rateController.addRate(rate, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void createRateUserNotFound() {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(new User());

        assertThrows(ManagerException.class, () -> {
            BindingResult result = new BeanPropertyBindingResult(rate, "request");
            ResponseEntity responseEntity = rateController.addRate(rate, result);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        });
    }

    @Test
    public void editRateWorks() throws Exception {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());
        rateRepository.save(rate);
        rateId = rate.getId();
        rate = rateRepository.findById(rateId).orElseThrow(() -> new Exception("Pas de rate"));

        rate.setRate(1);
        rate.setComment("Ok");

        BindingResult result = new BeanPropertyBindingResult(rate, "request");
        ResponseEntity responseEntity = rateController.editRate(rate, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(rateRepository.findById(rateId).get().getRate(), 1);
        assertEquals(rateRepository.findById(rateId).get().getComment(), "Ok");
    }

    @Test
    public void editRateNotFound() throws Exception {
        Rate rate = RateBuilderTest.create(null, 3, "Bien joué");
        rate.setTrade(tradeRepository.findById(tradeId).get());
        rate.setUser(userRepository.findById(userId).get());
        rateRepository.save(rate);
        rateId = rate.getId();

        rate = rateRepository.findById(rateId).orElseThrow(() -> new Exception("Pas de rate"));

        rate.setRate(1);
        rate.setComment("Ok");
        rate.setId(-10);

        Rate finalRate = rate;
        assertThrows(ManagerException.class, () -> {
            BindingResult result = new BeanPropertyBindingResult(finalRate, "request");
            ResponseEntity responseEntity = rateController.addRate(finalRate, result);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        });
    }

}
