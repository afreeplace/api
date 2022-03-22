package seyfa.afreeplace.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.services.geocoding.GeocodingService;
import seyfa.afreeplace.utils.AddressBuilderTest;
import seyfa.afreeplace.utils.UserBuilderTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AddressRepositoryTest {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    GeocodingService geocodingService;

    int addressId, secondAddressId;

    @Test
    public void ok() {
        assertNotNull(addressRepository);
    }

    @BeforeEach
    public void before() throws Exception {
        Address address = geocodingService.findByAddress("51 Rue de Rivoli").get(0);
        System.err.println(address);
        addressId = AddressBuilderTest.create(addressRepository, address).getId();
    }

    @AfterEach
    public void after() {
        AddressBuilderTest.delete(addressId, addressRepository);
    }

}
