package seyfa.afreeplace.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import seyfa.afreeplace.entities.Trade;
import seyfa.afreeplace.entities.User;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;

import java.time.LocalDateTime;

@Configuration
public class TradeBuilderTest {

    final static Logger logger = LoggerFactory.getLogger(TradeBuilderTest.class);

    public static Trade create(TradeRepository tradeRepository, String name, Trade.Status status) {
        Trade trade = new Trade();
        trade.setName(name);
        trade.setPhoneNumber("06.47.10.13.19");
        trade.setDescription("Entreprise tech pour les projets utiles");
        trade.setCreationDate(LocalDateTime.now());
        trade.setStatus(status);

        if(tradeRepository != null) {
            tradeRepository.save(trade);
            logger.info("Trade {} registered for tests", trade.getId());
        }

        return trade;
    }

    public static void delete(int tradeId, TradeRepository tradeRepository) {
        Trade trade = tradeRepository.findById(tradeId).orElse(null);
        if(trade != null) {
            tradeRepository.delete(tradeRepository.findById(tradeId).get());
            logger.info("Deleted trade " + tradeId);
        } else {
            logger.info("Deleted trade " + tradeId);
        }
    }
    
}
