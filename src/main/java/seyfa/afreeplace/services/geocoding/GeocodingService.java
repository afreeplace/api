package seyfa.afreeplace.services.geocoding;

import seyfa.afreeplace.entities.business.Address;

import java.util.List;

public interface GeocodingService {

    List<Address> findByAddress(String address) throws Exception;

}
