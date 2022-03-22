package seyfa.afreeplace.controllers;

import seyfa.afreeplace.entities.request.PasswordRequest;
import seyfa.afreeplace.entities.business.User;
import seyfa.afreeplace.managers.UserManager;
import seyfa.afreeplace.utils.response.BindingResultWrapper;
import seyfa.afreeplace.utils.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    UserManager userManager;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createAccuont(@Valid @RequestBody User user, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        userManager.create(user);

        result.put("message", "Account created");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update/data")
    public ResponseEntity<Map<String, Object>> updateAccuont(@Valid @RequestBody User user, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        userManager.update(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update/credentials/{userId}")
    public ResponseEntity<Map<String, Object>> updateCredentials(
            @PathVariable("userId") int userId,
            @Valid @RequestBody PasswordRequest passwordRequest,
            BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);

        userManager.changePassword(userId, passwordRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/delete/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable int userId) {
        Map result = new HashMap();

        userManager.delete(userId);

        result.put("message", "App Starter is working");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
