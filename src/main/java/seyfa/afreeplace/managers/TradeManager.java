package seyfa.afreeplace.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.Trade;
import seyfa.afreeplace.entities.User;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class TradeManager implements IManager<Trade, Integer> {

    Logger logger = LoggerFactory.getLogger(TradeManager.class);

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Trade find(Integer id) throws ManagerException {
        return tradeRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
    }

    @Override
    public void create(Trade trade) throws ManagerException {
        Trade newTrade = new Trade();

        if(trade.getOwner() == null) throw new ManagerException(ExceptionConstants.userNotFound());
        User foundOwner = userRepository.findById(trade.getOwner().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));

        // mandatory
        newTrade.setName(trade.getName());
        newTrade.setPhoneNumber(trade.getPhoneNumber());
        newTrade.setDescription(trade.getDescription());
        newTrade.setAvailable(true);
        newTrade.setStatus(Trade.Status.REQUESTED);
        newTrade.setOwner(foundOwner);
        newTrade.setCreationDate(LocalDateTime.now());
        // non mandatory
        newTrade.setLogoUrl(trade.getLogoUrl());
        newTrade.setWebsiteUrl(trade.getWebsiteUrl());

        tradeRepository.save(newTrade);
    }

    @Override
    public void update(Trade object) throws ManagerException {
        Trade trade =  tradeRepository.findById(object.getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));

        trade.setName(object.getName());
        trade.setPhoneNumber(object.getPhoneNumber());
        trade.setDescription(object.getDescription());
        trade.setLogoUrl(object.getLogoUrl());
        trade.setWebsiteUrl(object.getWebsiteUrl());

        tradeRepository.save(trade); 
    }

    @Override
    public void delete(Integer id) throws ManagerException {
        // TODO : add business rules
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
        tradeRepository.delete(trade);
    }
}
