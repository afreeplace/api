package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import seyfa.afreeplace.Application;
import seyfa.afreeplace.entities.business.Hours;
import seyfa.afreeplace.entities.business.ScheduleDay;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.exceptions.ManagerException;
import seyfa.afreeplace.managers.HoursManager;
import seyfa.afreeplace.repositories.DayRepository;
import seyfa.afreeplace.repositories.HoursRepository;
import seyfa.afreeplace.repositories.TradeRepository;
import seyfa.afreeplace.utils.TradeBuilderTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ScheduleControllerTest {

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    int tradeId;
    int dayId;

    String name = "Seyfa Tech";
    Trade.Status status = Trade.Status.REQUESTED;

    @Autowired
    ScheduleController scheduleController;

    @Autowired
    HoursRepository hoursRepository;

    @Autowired
    DayRepository dayRepository;

    @Autowired
    TradeRepository tradeRepository;


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
        assertNotNull(scheduleController);
    }

    @Test
    public void addSpecifidDayWorks() {
        dayRepository.deleteAll();

        ScheduleDay day = new ScheduleDay();
        day.setSpecificDate(LocalDate.now());
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        BindingResult result = new BeanPropertyBindingResult(day, "request");
        ResponseEntity responseEntity = scheduleController.addScheduleDay(day, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addSpecifidDayTwice() {
        dayRepository.deleteAll();

        ScheduleDay day = new ScheduleDay();
        day.setSpecificDate(LocalDate.now());
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        BindingResult result = new BeanPropertyBindingResult(day, "request");
        ResponseEntity responseEntity = scheduleController.addScheduleDay(day, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertThrows(ManagerException.class, () -> {
            ResponseEntity responseEntity2 = scheduleController.addScheduleDay(day, result);
            HttpStatus httpStatus  = responseEntity2.getStatusCode();
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        });

    }

    @Test
    public void addGeneralDayWorks() {
        dayRepository.deleteAll();

        ScheduleDay day = new ScheduleDay();
        day.setDayOfWeek(DayOfWeek.MONDAY);
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        BindingResult result = new BeanPropertyBindingResult(day, "request");
        ResponseEntity responseEntity = scheduleController.addScheduleDay(day, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addGeneralDayTwice() {
        dayRepository.deleteAll();

        ScheduleDay day = new ScheduleDay();
        day.setDayOfWeek(DayOfWeek.MONDAY);
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());

        BindingResult result = new BeanPropertyBindingResult(day, "request");
        ResponseEntity responseEntity = scheduleController.addScheduleDay(day, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertThrows(ManagerException.class, () -> {
            ResponseEntity responseEntity2 = scheduleController.addScheduleDay(day, result);
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        });

    }

    @Test
    public void deleteWorks() {

        ScheduleDay day = new ScheduleDay();
        day.setDayOfWeek(DayOfWeek.MONDAY);
        day.setOpen(false);
        day.setTrade(tradeRepository.findById(tradeId).get());
        dayRepository.save(day);

        BindingResult result = new BeanPropertyBindingResult(day, "request");
        ResponseEntity responseEntity = scheduleController.removeScheduleDay(day.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    // -- hours --

    @Test
    public void testAddHoursWorks() {
        Hours hours = new Hours();
        hours.setDay(dayRepository.findById(dayId).get());
        hours.setBegin(LocalTime.now().plusHours(1));
        hours.setEnd(LocalTime.now().plusHours(3));

        BindingResult result = new BeanPropertyBindingResult(hours, "request");
        ResponseEntity responseEntity = scheduleController.addHours(hours, result);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void testAddingBadHours() {
        Hours hours = new Hours();
        hours.setDay(dayRepository.findById(dayId).get());
        hours.setBegin(LocalTime.of(11, 0));
        hours.setEnd(LocalTime.of(9, 0));

        Hours finalHours = hours;
        assertThrows(ManagerException.class, () -> {
            BindingResult result = new BeanPropertyBindingResult(hours, "request");
            ResponseEntity responseEntity2 = scheduleController.addHours(hours, result);
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity2.getStatusCode());
        });
    }

    @Test
    public void deleteHours() {
        Hours hours = new Hours();
        hours.setDay(dayRepository.findById(dayId).get());
        hours.setBegin(LocalTime.now().plusHours(1));
        hours.setEnd(LocalTime.now().plusHours(3));
        hours = hoursRepository.save(hours);

        ResponseEntity responseEntity2 = scheduleController.removeHours(hours.getId());
        assertEquals(HttpStatus.OK, responseEntity2.getStatusCode());
    }

}
