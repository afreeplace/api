package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Hours;
import seyfa.afreeplace.entities.business.ScheduleDay;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.DayRepository;
import seyfa.afreeplace.repositories.HoursRepository;
import seyfa.afreeplace.utils.constants.ExceptionConstants;

@Service
@Transactional
public class HoursManager {

    @Autowired
    DayRepository dayRepository;

    @Autowired
    HoursRepository hoursRepository;

    public Hours create(Hours hours) {
        ScheduleDay day = dayRepository.findById(hours.getDay().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.dataNotFound()));

        if(day.getHours().contains(hours)) {
            throw new ManagerException(ExceptionConstants.badHoursSet());
        }

        int difference = (hours.getEnd().getHour() - hours.getBegin().getHour());
        if( difference <= 0 ) {
            throw new ManagerException(ExceptionConstants.badHoursSet());
        }

        Hours newHours = new Hours();
        newHours.setBegin(hours.getBegin());
        newHours.setEnd(hours.getEnd());
        newHours.setDay(day);

        hoursRepository.save(newHours);
        day.getHours().add(hours);

        return newHours;
    }

    public void delete(int hoursId) {
        Hours hours = hoursRepository.findById(hoursId).orElseThrow(() -> new ManagerException(ExceptionConstants.dataNotFound()));
        ScheduleDay day = dayRepository.findById(hours.getDay().getId()).orElseThrow(() -> new ManagerException(ExceptionConstants.dataNotFound()));
        day.getHours().remove(hours);
        hoursRepository.delete(hours);
    }

}
