package seyfa.afreeplace.services.geocoding;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.Application;
import seyfa.afreeplace.entities.Address;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GoogleGeocodingTest {

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    GoogleGeocodingService googleGeocodingService;


    @Test
    public void ok() {
        assertNotNull(googleGeocodingService);
    }

    @Test
    public void testExistingAddress() throws Exception {
        String address = "Rue des champs elysées";

        Address address1 = googleGeocodingService.findByAddress(address);
    }

}