package seyfa.afreeplace.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.managers.AddressManager;
import seyfa.afreeplace.utils.response.BindingResultWrapper;
import seyfa.afreeplace.utils.response.ResponseObject;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("address")
public class AddressController {

    Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    AddressManager addressManager;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createAddress(@Valid @RequestBody Address address, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        int addressId = addressManager.create(address);

        result.put("address", addressManager.find(addressId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/check/{address}")
    public ResponseEntity<Map<String, Object>> checkAddress(@PathVariable("address") String address) throws Exception {
        Map result = ResponseObject.map();

        List<Address> foundAddresses = addressManager.verifyAddress(address);

        result.put("addresses", foundAddresses);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/delete/{addressId}")
    public ResponseEntity<Map<String, Object>> deleteAddress(@PathVariable("addressId") int addressId) {
        Map result = ResponseObject.map();
        addressManager.delete(addressId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
