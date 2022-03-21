package seyfa.afreeplace.services.geocoding;

import seyfa.afreeplace.entities.Address;

public interface GeocodingService {

    Address findByAddress(String address) throws Exception;

    Address findByPlaceId(String placeId);

}
