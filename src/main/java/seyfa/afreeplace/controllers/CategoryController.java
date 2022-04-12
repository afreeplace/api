package seyfa.afreeplace.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seyfa.afreeplace.entities.business.Category;
import seyfa.afreeplace.entities.business.Tag;
import seyfa.afreeplace.managers.CategoryManager;
import seyfa.afreeplace.repositories.CategoryRepository;
import seyfa.afreeplace.utils.response.ResponseObject;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    CategoryManager categoryManager;

    @GetMapping("/find/all")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map result = ResponseObject.map();

        List<Category> categories = categoryManager.findAll();

        result.put("categories", categories);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/generate")
    public ResponseEntity<Map<String, Object>> generate() {
        categoryManager.generate();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
