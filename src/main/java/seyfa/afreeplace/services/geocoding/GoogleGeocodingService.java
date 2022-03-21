package seyfa.afreeplace.services.geocoding;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.Address;

@Service
public class GoogleGeocodingService implements GeocodingService {
    Logger logger = LoggerFactory.getLogger(GoogleGeocodingService.class);

    @Autowired
    GeoApiContext geocodingContext;

    @Override
    public Address findByAddress(String address) throws Exception {
        Address foundAddress = new Address();
        GeocodingApiRequest request = GeocodingApi.newRequest(geocodingContext).address(address);

        try {
            GeocodingResult[] result = request.await();
            System.out.println("Found address");

            for (GeocodingResult geocodingResult : result) {
                logger.info("GeoResult : " + geocodingResult);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }

        return foundAddress;
    }

    @Override
    public Address findByPlaceId(String placeId) {
        return null;
    }
}
