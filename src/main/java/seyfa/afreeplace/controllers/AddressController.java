//package seyfa.afreeplace.controllers;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import seyfa.afreeplace.entities.business.Address;
//import seyfa.afreeplace.entities.business.Trade;
//import seyfa.afreeplace.utils.response.BindingResultWrapper;
//import seyfa.afreeplace.utils.response.ResponseObject;
//
//import javax.validation.Valid;
//import java.util.Map;
//
//@RestController
//@RequestMapping("address")
//public class AddressController {
//
//    @PostMapping("/create")
//    public ResponseEntity<Map<String, Object>> createAddress(@Valid @RequestBody Address address, BindingResult bindingResult) {
//        Map result = ResponseObject.map();
//        BindingResultWrapper.checkFormErrors(bindingResult);
//
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<Map<String, Object>> updateAddress(@Valid @RequestBody Address address, BindingResult bindingResult) {
//        Map result = ResponseObject.map();
//        BindingResultWrapper.checkFormErrors(bindingResult);
//
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("/check/{address}")
//    public ResponseEntity<Map<String, Object>> checkAddress(@PathVariable("address") String address) {
//        Map result = ResponseObject.map();
//
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("/delete/{addressId}")
//    public ResponseEntity<Map<String, Object>> deleteAddress(@PathVariable("addressId") int addressId) {
//        Map result = ResponseObject.map();
//
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//}
