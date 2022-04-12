package seyfa.afreeplace.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.business.Category;
import seyfa.afreeplace.entities.business.Tag;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.CategoryRepository;
import seyfa.afreeplace.repositories.TagRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.repositories.UserRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class TradeManager implements IManager<Trade, Integer> {

    Logger logger = LoggerFactory.getLogger(TradeManager.class);

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Trade find(Integer id) throws ManagerException {
        return tradeRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
    }

    @Override
    public Integer create(Trade trade) throws ManagerException {
        Trade newTrade = new Trade();

        logger.info("Trade {}", trade);

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
        logger.info("Manager created thade {}");
        return newTrade.getId();
    }

    @Override
    public void update(Trade object) throws ManagerException {
        logger.info("Trying updating trade {}", object.getId());
        Trade trade =  tradeRepository.findById(object.getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));

        trade.setName(object.getName());
        trade.setPhoneNumber(object.getPhoneNumber());
        trade.setDescription(object.getDescription());
        trade.setLogoUrl(object.getLogoUrl());
        trade.setWebsiteUrl(object.getWebsiteUrl());
        logger.info("Updated:  {}", trade);

        tradeRepository.save(trade); 
    }

    @Override
    public void delete(Integer id) throws ManagerException {
        // TODO : add business rules
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
        tradeRepository.delete(trade);
    }

    public void addTag(int tradeId, int tagId) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ManagerException(ExceptionConstants.tagNotFound()));

        if(!trade.getTags().contains(tag)) {
            trade.getTags().add(tag);
        }
    }

    public void removeTag(int tradeId, int tagId) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ManagerException(ExceptionConstants.tagNotFound()));

        if(trade.getTags().contains(tag)) {
            trade.getTags().remove(tag);
        }
    }

    public void addCategory(int tradeId, int categoryId) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ManagerException(ExceptionConstants.categoryNotFound()));

        if(!trade.getCategories().contains(category)) {
            trade.getCategories().add(category);
            logger.info("Category '{}' added to trade '{}'", categoryId, tradeId);
        }
    }

    public void removeCategory(int tradeId, int categoryId) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ManagerException(ExceptionConstants.tagNotFound()));

        if(trade.getCategories().contains(category)) {
            trade.getCategories().remove(category);
            logger.info("Category '{}' removed from trade '{}'", categoryId, tradeId);
        }
    }

}