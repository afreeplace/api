package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.services.geocoding.GeocodingService;
import seyfa.afreeplace.services.geocoding.GoogleGeocodingService;

public class AddressManager implements IManager<Address, Integer> {

    @Autowired
    GeocodingService geocodingService;

    @Override
    public Address find(Integer integer) throws ManagerException {
        return null;
    }

    @Override
    public Integer create(Address address) throws ManagerException {
        return null;
    }

    @Override
    public void update(Address object) throws ManagerException {

    }

    @Override
    public void delete(Integer integer) throws ManagerException {

    }
}
