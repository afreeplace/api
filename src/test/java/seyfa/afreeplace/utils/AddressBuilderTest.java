package seyfa.afreeplace.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.repositories.AddressRepository;


public class AddressBuilderTest {

    final static Logger logger = LoggerFactory.getLogger(TradeBuilderTest.class);

    public static Address create(AddressRepository addressRepository, Address address) {

        if(addressRepository != null) {
            addressRepository.save(address);
            logger.info("Address {} registered for tests", address.getId());
        }

        return address;
    }

    public static void delete(int addressId, AddressRepository addressRepository) {
        Address address = addressRepository.findById(addressId).orElse(null);
        if(address != null) {
            addressRepository.delete(addressRepository.findById(addressId).get());
            logger.info("Deleted address " + addressId);
        } else {
            logger.info("Deleted address " + addressId);
        }
    }

}
