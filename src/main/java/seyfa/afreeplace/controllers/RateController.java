package seyfa.afreeplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seyfa.afreeplace.entities.business.Rate;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.managers.RateManager;
import seyfa.afreeplace.utils.response.BindingResultWrapper;
import seyfa.afreeplace.utils.response.ResponseObject;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("rate")
public class RateController {

    @Autowired
    RateManager rateManager;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> addRate(@Valid @RequestBody Rate rate, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        Rate newRate = rateManager.create(rate);

        result.put("rate", newRate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/edit")
    public ResponseEntity<Map<String, Object>> editRate (@Valid @RequestBody Rate rate, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        rateManager.edit(rate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
