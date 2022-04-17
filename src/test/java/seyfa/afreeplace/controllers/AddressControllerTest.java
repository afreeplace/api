package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.entities.request.UserRequest;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.AddressRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.services.geocoding.GeocodingService;
import seyfa.afreeplace.utils.AddressBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressControllerTest {

    @Autowired
    AddressController addressController;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    GeocodingService geocodingService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRequest userRequest;

    int userId, tradeId;
    int addressId, secondAddressId;

    Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);

    @BeforeEach
    public void before() throws Exception {
        User user = UserBuilderTest.create(userRepository, "mail@gmail.com", "password");
        userId = user.getId();
        userRequest.setAuthUser(user);

        Trade trade = TradeBuilderTest.create(tradeRepository, "Seyfa", Trade.Status.VALIDATED);
        tradeId = trade.getId();
        trade.setOwner(user);
        tradeRepository.save(trade);
    }

    @AfterEach
    public void after() {
        UserBuilderTest.delete(userId, userRepository);
        AddressBuilderTest.delete(addressId, addressRepository);
    }

    public void ok() {
        assertNotNull(addressController);
    }

    @Test
    public void createWithNoOwnerFails() throws Exception {
        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
        address.setTrade(new Trade()
        );

        assertThrows(ManagerException.class, () -> {
            BindingResult result = new BeanPropertyBindingResult(address, "request");
            addressController.createAddress(address, result);
        });
    }

    @Test
    public void createWorks() throws Exception {

        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
        address.setTrade(tradeRepository.findById(tradeId).get());

        BindingResult result = new BeanPropertyBindingResult(address, "request");

        ResponseEntity<Map<String, Object>> response = addressController.createAddress(address, result);
        Map<String, Object> body = response.getBody();
        addressId = ((Address) body.get("address")).getId();

        assertNotNull(addressRepository.findById(addressId).get());
    }

    @Test
    public void deleteExistingAddressWorks() throws Exception {
        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
        address.setTrade(tradeRepository.findById(tradeId).get());
        addressId = AddressBuilderTest.create(addressRepository, address).getId();

        addressController.deleteAddress(addressId);
        assertNull(addressRepository.findById(addressId).orElse(null));
    }

    @Test
    public void deleteNonExistingAddressFails() {
        assertThrows(ManagerException.class, () -> {
            addressController.deleteAddress(-1);
        });
    }
}
