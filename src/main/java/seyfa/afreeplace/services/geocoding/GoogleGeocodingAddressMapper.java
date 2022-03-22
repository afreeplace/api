package seyfa.afreeplace.services.geocoding;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.business.Address;

@Service
public class GoogleGeocodingAddressMapper {

    Logger logger = LoggerFactory.getLogger(GoogleGeocodingAddressMapper.class);

    private final static String TYPES_POSTAL_CODE = "postal_code";
    private final static String TYPES_CITY        = "locality";
    private final static String TYPES_COUNTRY     = "country";
    private final static String TYPES_REGION      = "administrative_area_level_1";

    public Address map(GeocodingResult geocodingResult) {
        Address address = new Address();

        address.setPlaceId(geocodingResult.placeId);
        address.setLatitude(geocodingResult.geometry.location.lat);
        address.setLongitude(geocodingResult.geometry.location.lng);
        address.setRoute(geocodingResult.formattedAddress.split(",")[0]);

        for(AddressComponent addressComponent: geocodingResult.addressComponents) {

            switch (addressComponent.types[0].toString()) {
                case TYPES_POSTAL_CODE:
                    address.setPostalCode(addressComponent.longName);
                    break;
                case TYPES_CITY:
                    address.setCity(addressComponent.longName);
                    break;
                case TYPES_COUNTRY:
                    address.setCountry(addressComponent.longName);
                    break;
                case TYPES_REGION:
                    address.setRegion(addressComponent.longName);
                    break;
            }

        }

        return address;
    }

}
