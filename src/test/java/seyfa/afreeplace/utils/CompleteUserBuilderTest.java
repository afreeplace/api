package seyfa.afreeplace.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.business.*;
import seyfa.afreeplace.entities.request.UserRequest;
import seyfa.afreeplace.managers.*;
import seyfa.afreeplace.repositories.*;
import seyfa.afreeplace.services.geocoding.GeocodingService;

import java.time.LocalTime;

@Service
public class CompleteUserBuilderTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    GeocodingService geocodingService;

    @Autowired
    DayRepository dayRepository;

    @Autowired
    HoursRepository hoursRepository;


    public int create(String email) throws Exception {
        User user = UserBuilderTest.create(userRepository, email, "password");

        Trade trade = TradeBuilderTest.create(tradeRepository, "name", Trade.Status.VALIDATED);
        trade.setOwner(user);

        Address address = AddressBuilderTest.create(addressRepository, geocodingService.findByAddress("Champs elysées").get(0));
        address.setTrade(trade);
        trade.setAddress(address);

        ScheduleDay scheduleDay = DayBuilderTest.create(dayRepository);
        scheduleDay.setTrade(trade);
        trade.getSchedule().add(scheduleDay);

        Hours hours = new Hours();
        hours.setBegin(LocalTime.now());
        hours.setEnd(LocalTime.now());
        hours.setDay(scheduleDay);
        hoursRepository.save(hours);
        scheduleDay.getHours().add(hours);

        return user.getId();
    }
}
