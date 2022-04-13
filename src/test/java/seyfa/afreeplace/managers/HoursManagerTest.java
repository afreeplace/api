package seyfa.afreeplace.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Hours;
import seyfa.afreeplace.entities.business.ScheduleDay;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.DayRepository;
import seyfa.afreeplace.repositories.HoursRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.utils.TradeBuilderTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class HoursManagerTest {

    @Autowired
    HoursManager hoursManager;

    @Autowired
    HoursRepository hoursRepository;

    @Autowired
    DayRepository dayRepository;

    int tradeId;
    int dayId;

    @Autowired
    TradeRepository tradeRepository;

    String name = "Seyfa Tech";
    Trade.Status status = Trade.Status.REQUESTED;

    @BeforeEach
    public void before() throws Exception {
        tradeId = TradeBuilderTest.create(tradeRepository, name,status).getId();

        ScheduleDay day = new ScheduleDay();
        day.setSpecificDate(LocalDate.now());
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());
        dayId = dayRepository.save(day).getId();

        assertNotNull(tradeRepository.findById(tradeId).orElse(null));
        assertNotNull(dayRepository.findById(dayId).orElse(null));
    }

    @AfterEach
    public void after() {
        TradeBuilderTest.delete(tradeId, tradeRepository);
        assertNull(tradeRepository.findById(tradeId).orElse(null));
    }

    @Test
    public void ok() {
        assertNotNull(hoursManager);
    }

    @Test
    public void testAddHoursWorks() {
        Hours hours = new Hours();
        hours.setDay(dayRepository.findById(dayId).get());
        hours.setBegin(LocalTime.now().plusHours(1));
        hours.setEnd(LocalTime.now().plusHours(3));

        hours = hoursManager.create(hours);

        assertNotNull(hoursRepository.findById(hours.getId()).orElse(null));
        assertEquals(1, dayRepository.findById(dayId).get().getHours().size());
    }

    @Test
    public void testAddHoursTwice() {
        Hours hours = new Hours();
        hours.setDay(dayRepository.findById(dayId).get());
        hours.setBegin(LocalTime.now().plusHours(1));
        hours.setEnd(LocalTime.now().plusHours(3));

        hours = hoursManager.create(hours);

        Hours finalHours = hours;
        assertThrows(ManagerException.class, () -> {
           hoursManager.create(finalHours);
        });
    }

    @Test
    public void testAddingBadHours() {
        Hours hours = new Hours();
        hours.setDay(dayRepository.findById(dayId).get());
        hours.setBegin(LocalTime.of(11, 0));
        hours.setEnd(LocalTime.of(9, 0));

        Hours finalHours = hours;
        assertThrows(ManagerException.class, () -> {
            hoursManager.create(finalHours);
        });

    }

    @Test
    public void deleteHours() {
        Hours hours = new Hours();
        hours.setDay(dayRepository.findById(dayId).get());
        hours.setBegin(LocalTime.now().plusHours(1));
        hours.setEnd(LocalTime.now().plusHours(3));
        hours = hoursManager.create(hours);

        hoursManager.delete(hours.getId());
        assertNull(hoursRepository.findById(hours.getId()).orElse(null));
        assertEquals(0, dayRepository.findById(dayId).orElse(null).getHours().size());
    }
}