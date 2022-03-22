package seyfa.afreeplace.managers;

import org.apache.catalina.Manager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.AddressRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.services.geocoding.GeocodingService;
import seyfa.afreeplace.utils.AddressBuilderTest;
import seyfa.afreeplace.utils.TradeBuilderTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressManagerTest {

    Logger logger = LoggerFactory.getLogger(AddressManagerTest.class);

    @Autowired
    AddressManager addressManager;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    GeocodingService geocodingService;

    int addressId, secondAddressId;

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
           addressManager.create(address);
        });
    }

    @Test
    public void createWorks() throws Exception {
        Trade trade = TradeBuilderTest.create(tradeRepository, "Seyfa tech", Trade.Status.VALIDATED);

        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
        address.setTrade(trade);
        addressId = addressManager.create(address);

        logger.info("Addr {}", addressRepository.findById(addressId).get());
        assertNotNull(addressRepository.findById(addressId).get());
//        assertNotNull(addressRepository.findById(addressId).get().getTrade());
        assertNotNull(tradeRepository.findById(trade.getId()).get().getAddress());

        TradeBuilderTest.delete(trade.getId(), tradeRepository);
        assertNull(addressRepository.findById(addressId).orElse(null));
    }

    @Test
    public void deleteExistingAddressWorks() throws Exception {
        Trade trade = TradeBuilderTest.create(tradeRepository, "Seyfa tech", Trade.Status.VALIDATED);
        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);

        address.setTrade(trade);
        addressId = AddressBuilderTest.create(addressRepository, address).getId();

        addressManager.delete(addressId);

        assertNull(addressRepository.findById(addressId).orElse(null));
        TradeBuilderTest.delete(trade.getId(), tradeRepository);
    }

    @Test
    public void deleteNonExistingAddressFails() {
        assertThrows(ManagerException.class, () -> {
            addressManager.delete(-1);
        });
    }

}
