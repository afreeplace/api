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
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.AddressRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.services.geocoding.GeocodingService;
import seyfa.afreeplace.utils.AddressBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;

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

    int addressId, secondAddressId;

    Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);

    @BeforeEach
    public void before() throws Exception {
        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
        System.err.println(address);
        addressId = AddressBuilderTest.create(null, address).getId();
    }

    @AfterEach
    public void after() {
        AddressBuilderTest.delete(addressId, addressRepository);
    }

    @Test
    public void createWithNoOwnerFails() throws Exception {
        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
        assertThrows(ManagerException.class, () -> {
            BindingResult result = new BeanPropertyBindingResult(address, "request");
            addressController.createAddress(address, result);
        });
    }

    @Test
    public void createWorks() throws Exception {
        Trade trade = TradeBuilderTest.create(tradeRepository, "Seyfa tech", Trade.Status.VALIDATED);

        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
        address.setTrade(trade);

        BindingResult result = new BeanPropertyBindingResult(address, "request");

        ResponseEntity<Map<String, Object>> response = addressController.createAddress(address, result);
        Map<String, Object> body = response.getBody();
        addressId = ((Address) body.get("address")).getId();

        assertNotNull(addressRepository.findById(addressId).get());
    }

    @Test
    public void deleteExistingAddressWorks() throws Exception {
        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
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
