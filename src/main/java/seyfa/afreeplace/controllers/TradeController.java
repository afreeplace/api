package seyfa.afreeplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import seyfa.afreeplace.entities.Trade;
import seyfa.afreeplace.managers.TradeManager;
import seyfa.afreeplace.utils.response.BindingResultWrapper;
import seyfa.afreeplace.utils.response.ResponseObject;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("trade")
public class TradeController {

    @Autowired
    TradeManager tradeManager;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createTrade(@Valid @RequestBody Trade trade, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        int tradeId = tradeManager.create(trade);
        Trade createdTrade =  tradeManager.find(tradeId);

        result.put("trade", createdTrade);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update/data")
    public ResponseEntity<Map<String, Object>> updateTrade(@Valid @RequestBody Trade trade, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        tradeManager.update(trade);

        result.put("trade", tradeManager.find(trade.getId()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/delete/{tradeId}")
    public ResponseEntity<Map<String, Object>> deleteTrade(@PathVariable int tradeId) {
        Map result = new HashMap();

        tradeManager.delete(tradeId);

        result.put("message", "App Starter is working");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
