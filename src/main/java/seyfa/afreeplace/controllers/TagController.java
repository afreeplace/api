package seyfa.afreeplace.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Tag;
import seyfa.afreeplace.managers.TagManager;
import seyfa.afreeplace.repositories.TagRepository;
import seyfa.afreeplace.utils.response.BindingResultWrapper;
import seyfa.afreeplace.utils.response.ResponseObject;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tag")
public class TagController {

    Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    TagManager tagManager;

    @Autowired
    TagRepository tagRepository;

    @GetMapping("/find/{tagId}")
    public ResponseEntity<Map<String, Object>> findTagById(@PathVariable("tagId") int tagId) {
        Map result = ResponseObject.map();

        Tag tag = tagManager.find(tagId);

        result.put("tag", tag);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/generate")
    public ResponseEntity<Map<String, Object>> generate() {
        Map result = ResponseObject.map();

        Tag tag = new Tag();
        tag.setLogoUrl("www.google.fr");
        tag.setCreationDate(LocalDateTime.now());
        tag.setName("Tag 1");

        Tag tag2 = new Tag();
        tag2.setLogoUrl("www.google.fr");
        tag2.setCreationDate(LocalDateTime.now());
        tag2.setName("Tag 2");

        Tag tag3 = new Tag();
        tag3.setLogoUrl("www.google.fr");
        tag3.setCreationDate(LocalDateTime.now());
        tag3.setName("Tag 3");

        tagRepository.save(tag);
        tagRepository.save(tag2);
        tagRepository.save(tag3);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/find/all")
    public ResponseEntity<Map<String, Object>> findAllTags() {
        Map result = ResponseObject.map();

        List<Tag> tags = tagManager.findAll();

        result.put("tags", tags);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
