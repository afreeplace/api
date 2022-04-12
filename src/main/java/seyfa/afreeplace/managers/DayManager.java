package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.ScheduleDay;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.DayRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DayManager {

    @Autowired
    DayRepository dayRepository;

    @Autowired
    TradeRepository tradeRepository;

    public ScheduleDay create(ScheduleDay day) {
        Trade trade = tradeRepository.findById(day.getTrade().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));

        ScheduleDay newDay = new ScheduleDay();

        if(day.getSpecificDate() != null) { // if specific day ex: '09/04/2020'
            List<ScheduleDay> sameDates = trade.getSchedule().stream().filter(existingDay -> existingDay.getSpecificDate() != null && (day.getSpecificDate().equals(existingDay.getSpecificDate()))).collect(Collectors.toList());
            boolean alreadyExists = sameDates.size() == 1;
            if (alreadyExists) throw new ManagerException(ExceptionConstants.badAvailability());
            newDay.setSpecificDate(day.getSpecificDate());
        } else {// if general day ex 'Monddy'
            // check that the same doesn't exist yet
            List<ScheduleDay> sameDays = trade.getSchedule().stream().filter(existingDay -> existingDay.getDayOfWeek() != null && (day.getDayOfWeek() == existingDay.getDayOfWeek())).collect(Collectors.toList());
            boolean alreadyExists = sameDays.size() == 1;
            if (alreadyExists) throw new ManagerException(ExceptionConstants.badAvailability());
            newDay.setDayOfWeek(day.getDayOfWeek());
        }

        newDay.setOpen(day.isOpen());
        newDay.setTrade(trade);
        dayRepository.save(newDay);

        trade = tradeRepository.findById(trade.getId()).get();
        trade.getSchedule().add(newDay);

        return newDay;
    }

    public void delete(int scheduleDayId) {
        ScheduleDay day = dayRepository.findById(scheduleDayId).orElseThrow(() -> new ManagerException(ExceptionConstants.dataNotFound()));

        Trade trade = tradeRepository.findById(day.getTrade().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.tradeNotFound()));
        trade.getSchedule().remove(day);

        dayRepository.delete(day);
    }

}
