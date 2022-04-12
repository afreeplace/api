package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Rate;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.managers.TradeManager;
import seyfa.afreeplace.repositories.RateRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

import java.time.LocalDateTime;

@Transactional
public class RateManager {

    private static int MAX_RATE = 5;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    public Rate create(Rate rate) {
        User user = userRepository.findById(rate.getUser().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));
        Trade trade = tradeRepository.findById(rate.getTrade().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));

        checkRate(user, trade, rate);

        Rate newRate = new Rate();
        newRate.setRate(rate.getRate());
        newRate.setComment(rate.getComment());
        newRate.setUser(user);
        newRate.setTrade(trade);
        newRate.setEditDate(LocalDateTime.now());
        rateRepository.save(newRate);

        return newRate;
    }

    public void edit(Rate rate) {
        Rate rateToEdit = rateRepository.findById(rate.getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.dataNotFound()));
        User user = userRepository.findById(rate.getUser().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));
        Trade trade = tradeRepository.findById(rate.getTrade().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));

        checkRate(user, trade, rate);

        rateToEdit.setRate(rate.getRate());
        rateToEdit.setComment(rate.getComment());
        rateToEdit.setEditDate(LocalDateTime.now());

        rateRepository.save(rateToEdit);
    }

    private void checkRate(User user, Trade trade, Rate rate) {
        if (trade.getOwner().equals(user)) {
            throw new ManagerException(ExceptionConstants.cannotDoThat());
        }

        if (rate.getRate() < 1 || rate.getRate() > MAX_RATE) {
            throw new ManagerException(ExceptionConstants.dataNotGood());
        }

        if (rateRepository.findByUserAndTrade(user.getId(), trade.getId()) != null) {
            throw new ManagerException(ExceptionConstants.cannotDoThat());
        }
    }
}
