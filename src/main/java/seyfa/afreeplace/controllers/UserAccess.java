package seyfa.afreeplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.*;
import seyfa.afreeplace.entities.request.UserRequest;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.*;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

@Service
@Transactional
public class UserAccess {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    UserRequest userRequest;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DayRepository dayRepository;

    @Autowired
    HoursRepository hoursRepository;

    public void hasRightToEditTrade(int tradeId) {
        int currentUserId = userRequest.getAuthUser().getId();
        User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> new ManagerException(ExceptionConstants.userNotFound()));
        // if trade == null : current user not owning trade, so can touch it
        Trade trade = tradeRepository.findTradeByOwnerId(currentUserId, tradeId).orElseThrow(() -> new ManagerException(ExceptionConstants.cannotDoThat()));

        if(!currentUser.getTrades().contains(trade)) {
            throw new ManagerException(ExceptionConstants.cannotDoThat());
        }
    }

    public void hasRighToEditAddress(int addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ManagerException(ExceptionConstants.addressNotFound()));
        this.hasRightToEditTrade(address.getTrade().getId());
    }

    public void hasRightToEditSchedule(int scheduleDayId) {
        ScheduleDay scheduleDay = dayRepository.findById(scheduleDayId).orElseThrow(() -> new ManagerException(ExceptionConstants.dataNotFound()));
        this.hasRightToEditTrade(scheduleDay.getTrade().getId());
    }

    public void hasRightToEditHours(int hoursId) {
        Hours hours = hoursRepository.findById(hoursId).orElseThrow(() -> new ManagerException(ExceptionConstants.dataNotFound()));
        this.hasRightToEditSchedule(hours.getDay().getId());
    }

}
