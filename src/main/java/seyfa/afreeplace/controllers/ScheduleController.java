package seyfa.afreeplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import seyfa.afreeplace.entities.business.Hours;
import seyfa.afreeplace.entities.business.ScheduleDay;
import seyfa.afreeplace.entities.business.Trade;
import seyfa.afreeplace.managers.DayManager;
import seyfa.afreeplace.managers.HoursManager;
import seyfa.afreeplace.utils.response.BindingResultWrapper;
import seyfa.afreeplace.utils.response.ResponseObject;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("schedule")
public class ScheduleController {

    @Autowired
    DayManager dayManager;

    @Autowired
    HoursManager hoursManager;

    @Autowired
    UserAccess userAccess;

    @PostMapping("/day/create")
    public ResponseEntity<Map<String, Object>> addScheduleDay(@Valid @RequestBody ScheduleDay scheduleDay, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        userAccess.hasRightToEditTrade(scheduleDay.getTrade().getId());
        ScheduleDay createdDay = dayManager.create(scheduleDay);

        result.put("day", createdDay);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/day/delete/{id}")
    public ResponseEntity<Map<String, Object>> removeScheduleDay(@PathVariable("id") int id) {
        Map result = ResponseObject.map();

        userAccess.hasRightToEditSchedule(id);
        dayManager.delete(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/hours/create")
    public ResponseEntity<Map<String, Object>> addHours(@Valid @RequestBody Hours hours, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        userAccess.hasRightToEditSchedule(hours.getDay().getId());
        Hours createdHours = hoursManager.create(hours);

        result.put("createdHours", createdHours);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/hours/delete/{id}")
    public ResponseEntity<Map<String, Object>> removeHours(@PathVariable("id") int id) {
        Map result = ResponseObject.map();

        userAccess.hasRightToEditHours(id);
        hoursManager.delete(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
