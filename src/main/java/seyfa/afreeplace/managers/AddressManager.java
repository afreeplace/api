package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.AddressRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.services.geocoding.GeocodingService;
import seyfa.afreeplace.services.geocoding.GoogleGeocodingService;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

import java.util.List;

@Service
@Transactional
public class AddressManager implements IManager<Address, Integer> {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    GeocodingService geocodingService;

    @Override
    public Address find(Integer id) throws ManagerException {
        return addressRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.addressNotFound()));
    }

    public List<Address> verifyAddress(String address) throws Exception, ManagerException {
        List<Address> addressList = geocodingService.findByAddress(address);
        return addressList;
    }

    @Override
    public Integer create(Address address) throws ManagerException {
        Address addressToCreate = new Address();

        if(address.getTrade() == null) throw new ManagerException(ExceptionConstants.tradeNotFound());
        Trade owner = tradeRepository.findById(address.getTrade().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));

        addressToCreate.setTrade(owner);
        addressToCreate.setPlaceId(address.getPlaceId());
        addressToCreate.setRoute(address.getRoute());
        addressToCreate.setPostalCode(address.getPostalCode());
        addressToCreate.setCity(address.getCity());
        addressToCreate.setRegion(address.getRegion());
        addressToCreate.setCountry(address.getCountry());
        addressToCreate.setLatitude(address.getLatitude());
        addressToCreate.setLongitude(address.getLongitude());
        addressToCreate.setDescription(address.getDescription());

        addressRepository.save(addressToCreate);
        return addressToCreate.getId();
    }

    @Override
    public void update(Address object) throws ManagerException {

    }

    @Override
    public void delete(Integer addressId) throws ManagerException {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ManagerException(ExceptionConstants.addressNotFound()));

        if(address.getTrade() != null) {
            Trade trade = tradeRepository.findById(address.getTrade().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
            trade.setAddress(null);
        }

        address.setTrade(null);
        addressRepository.delete(address);
    }
}
