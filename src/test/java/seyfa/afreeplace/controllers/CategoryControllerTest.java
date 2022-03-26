package seyfa.afreeplace.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import seyfa.afreeplace.entities.business.Category;
import seyfa.afreeplace.managers.CategoryManager;
import seyfa.afreeplace.repositories.CategoryRepository;
import seyfa.afreeplace.repositories.TagRepository;
import seyfa.afreeplace.utils.CategoryBuilderTest;
import seyfa.afreeplace.utils.TagBuilderTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class CategoryControllerTest {

    @Autowired
    CategoryController categoryController;

    @Autowired
    CategoryRepository categoryRepository;

    int catId1, catId2, catId3;

    @BeforeEach
    public void before() throws Exception {
        catId1 = CategoryBuilderTest.create(categoryRepository, "Category1").getId();
        catId2 = CategoryBuilderTest.create(categoryRepository, "Category2").getId();
        catId3 = CategoryBuilderTest.create(categoryRepository, "Category3").getId();
    }

    @AfterEach
    public void after() {
        CategoryBuilderTest.delete(catId1, categoryRepository);
        CategoryBuilderTest.delete(catId2, categoryRepository);
        CategoryBuilderTest.delete(catId3, categoryRepository);

        assertNull(categoryRepository.findById(catId1).orElse(null));
        assertNull(categoryRepository.findById(catId2).orElse(null));
        assertNull(categoryRepository.findById(catId3).orElse(null));
    }

    @Test
    public void testOk() {
        assertNotNull(categoryController);
    }

    @Test
    public void findAll() {
        ResponseEntity<Map<String, Object>> result = categoryController.findAll();
        List<Category> categories = (List<Category>) result.getBody().get("categories");
        assertEquals(3, categories.size());
    }


}
