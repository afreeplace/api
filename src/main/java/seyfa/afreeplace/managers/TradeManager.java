package seyfa.afreeplace.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.Trade;
import seyfa.afreeplace.exceptions.ManagerException;

import javax.transaction.Transactional;

@Service
@Transactional
public class TradeManager implements IManager<Trade, Integer> {

    Logger logger = LoggerFactory.getLogger(TradeManager.class);

    @Override
    public Trade find(Integer integer) throws ManagerException {
        return null;
    }

    @Override
    public void create(Trade trade) throws ManagerException {
        logger.info("Trade created");
    }

    @Override
    public void update(Trade object) throws ManagerException {
        logger.info("Trade created");
    }

    @Override
    public void delete(Integer integer) throws ManagerException {
        logger.info("Trade created");
    }
}
