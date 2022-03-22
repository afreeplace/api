package seyfa.afreeplace.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Photo;
import seyfa.afreeplace.managers.PhotoManager;
import seyfa.afreeplace.utils.response.BindingResultWrapper;
import seyfa.afreeplace.utils.response.ResponseObject;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("photo")
public class PhotoController {

    Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    PhotoManager photoManager;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPhoto(@Valid @RequestBody Photo photo, BindingResult bindingResult) {
        Map result = ResponseObject.map();
        BindingResultWrapper.checkFormErrors(bindingResult);
        photoManager.create(photo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/delete/{photoId}")
    public ResponseEntity<Map<String, Object>> deletePhoto(@PathVariable("photoId") int photoId) {
        Map result = ResponseObject.map();
        photoManager.delete(photoId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
