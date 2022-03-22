package seyfa.afreeplace.services.geocoding;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.business.Address;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class GoogleGeocodingService implements GeocodingService {

    Logger logger = LoggerFactory.getLogger(GoogleGeocodingService.class);

    @Autowired
    GeoApiContext geocodingContext;

    @Autowired
    GoogleGeocodingAddressMapper addressMapper;

    @Override
    public List<Address> findByAddress(String address) throws Exception {
        List<Address> foundAddresses = new ArrayList<>();

        GeocodingApiRequest request = GeocodingApi.newRequest(geocodingContext).address(address);

        GeocodingResult[] result = request.await();

        for (GeocodingResult geocodingResult : result) {
            foundAddresses.add(addressMapper.map(geocodingResult));
        }

        return foundAddresses;
    }

}
