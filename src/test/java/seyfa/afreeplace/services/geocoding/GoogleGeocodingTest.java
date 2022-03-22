package seyfa.afreeplace.services.geocoding;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.Application;
import seyfa.afreeplace.entities.business.Address;

import java.util.List;

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
        List<Address> foundAddresses = googleGeocodingService.findByAddress(address);

        logger.info("Nombre d'adresses trouvées {}", foundAddresses.size());

        foundAddresses.forEach(foundAddress -> {
            logger.info("------------------------");
            logger.info(foundAddress.getRoute());
            logger.info(foundAddress.getPostalCode());
            logger.info(foundAddress.getCity());
            logger.info(foundAddress.getRegion());
            logger.info("({}, {})", foundAddress.getLatitude(), foundAddress.getLongitude());
        });
    }

    @Test
    public void testMultipleAddress() throws Exception {
        String address = "rue belsunce";
        List<Address> foundAddresses = googleGeocodingService.findByAddress(address);

        logger.info("Nombre d'adresses trouvées {}", foundAddresses.size());

        foundAddresses.forEach(foundAddress -> {
            logger.info("------------------------");
            logger.info(foundAddress.getRoute());
            logger.info(foundAddress.getPostalCode());
            logger.info(foundAddress.getCity());
            logger.info(foundAddress.getRegion());
            logger.info("({}, {})", foundAddress.getLatitude(), foundAddress.getLongitude());
        });
    }

    @Test
    public void testMultipleAddress2() throws Exception {
        String address = "Rue de la République";
        List<Address> foundAddresses = googleGeocodingService.findByAddress(address);

        logger.info("Nombre d'adresses trouvées {}", foundAddresses.size());

        foundAddresses.forEach(foundAddress -> {
            logger.info("------------------------");
            logger.info(foundAddress.getRoute());
            logger.info(foundAddress.getPostalCode());
            logger.info(foundAddress.getCity());
            logger.info(foundAddress.getRegion());
            logger.info("({}, {})", foundAddress.getLatitude(), foundAddress.getLongitude());
        });
    }

    @Test
    public void testPlaceIdMatchsBetweenAddresses() throws Exception {
        String address = "51 Rue de Rivoli";
        List<Address> foundAddresses = googleGeocodingService.findByAddress(address);

        logger.info("Nombre d'adresses trouvées {}", foundAddresses.size());

        foundAddresses.forEach(foundAddress -> {
            logger.info("------------------------");
            logger.info(foundAddress.getPlaceId());
            logger.info(foundAddress.getRoute());
            logger.info(foundAddress.getPostalCode());
            logger.info(foundAddress.getCity());
            logger.info(foundAddress.getRegion());
            logger.info("({}, {})", foundAddress.getLatitude(), foundAddress.getLongitude());
        });
    }

}