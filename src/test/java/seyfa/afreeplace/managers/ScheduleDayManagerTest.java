package seyfa.afreeplace.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.Application;
import seyfa.afreeplace.entities.business.ScheduleDay;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.repositories.DayRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.utils.TradeBuilderTest;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ScheduleDayManagerTest {

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    int tradeId;

    @Autowired
    DayManager dayManager;

    @Autowired
    DayRepository dayRepository;

    @Autowired
    TradeRepository tradeRepository;

    String name = "Seyfa Tech";
    Trade.Status status = Trade.Status.REQUESTED;

    @BeforeEach
    public void before() throws Exception {
        tradeId = TradeBuilderTest.create(tradeRepository, name,status).getId();
    }

    @AfterEach
    public void after() {
        TradeBuilderTest.delete(tradeId, tradeRepository);
    }

    @Test
    public void ok() {
        assertNotNull(dayManager);
    }

    @Test
    public void addSpecifidDayWorks() {

        ScheduleDay day = new ScheduleDay();
        day.setSpecificDate(LocalDate.now());
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        dayManager.create(day);
        assertEquals(1, tradeRepository.findById(day.getTrade().getId()).get().getSchedule().size());

    }

    @Test
    public void addSpecifidDayTwice() {

        ScheduleDay day = new ScheduleDay();
        day.setSpecificDate(LocalDate.now());
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        day = dayManager.create(day);
        assertNotEquals(0, day.getId());

        ScheduleDay day1 = dayRepository.findById(day.getId()).get();

        assertEquals(1, tradeRepository.findById(day.getTrade().getId()).get().getSchedule().size());
        assertThrows(ManagerException.class, () -> {
            dayManager.create(day1);
        });

    }

    @Test
    public void addGeneralDayWorks() {

        ScheduleDay day = new ScheduleDay();
        day.setDayOfWeek(DayOfWeek.MONDAY);
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        dayManager.create(day);
        assertEquals(1, tradeRepository.findById(day.getTrade().getId()).get().getSchedule().size());

    }

    @Test
    public void addGeneralDayTwice() {

        ScheduleDay day = new ScheduleDay();
        day.setDayOfWeek(DayOfWeek.MONDAY);
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        day = dayManager.create(day);
        assertNotEquals(0, day.getId());

        ScheduleDay day1 = dayRepository.findById(day.getId()).get();

        assertEquals(1, tradeRepository.findById(day.getTrade().getId()).get().getSchedule().size());
        assertThrows(ManagerException.class, () -> {
            dayManager.create(day1);
        });

    }

    @Test
    public void deleteWorks() {

        ScheduleDay day = new ScheduleDay();
        day.setDayOfWeek(DayOfWeek.MONDAY);
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        day = dayManager.create(day);
        logger.info("Day {} created", day);
        assertEquals(1, tradeRepository.findById(day.getTrade().getId()).get().getSchedule().size());

        dayManager.delete(day.getId());
        assertNull(dayRepository.findById(day.getId()).orElse(null));
        Trade trade = tradeRepository.findById(day.getTrade().getId()).get();
        int nbDays = trade.getSchedule().size();
        assertEquals(0, nbDays);

    }

}
