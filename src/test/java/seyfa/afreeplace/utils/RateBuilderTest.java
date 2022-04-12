package seyfa.afreeplace.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.entities.business.Rate;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.repositories.RateRepository;
import seyfa.afreeplace.repositories.TradeRepository;

import java.time.LocalDateTime;

public class RateBuilderTest {

    final static Logger logger = LoggerFactory.getLogger(TradeBuilderTest.class);

    public static Rate create(RateRepository rateRepository, int rateNumber, String description) {
        Rate rate = new Rate();
        rate.setRate(rateNumber);
        rate.setComment(description);
        rate.setEditDate(LocalDateTime.now());

        if(rateRepository != null) {
            rateRepository.save(rate);
            logger.info("Rate {} registered for tests", rate.getId());
        }

        return rate;
    }

    public static void delete(int rateId, RateRepository rateRepository) {
        Rate rate = rateRepository.findById(rateId).orElse(null);
        if(rate != null) {
            rateRepository.delete(rateRepository.findById(rateId).get());
            logger.info("Deleted rate " + rateId);
        } else {
            logger.info("Deleted rate " + rateId);
        }
    }

}