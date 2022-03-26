package seyfa.afreeplace.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.utils.CategoryBuilderTest;
import seyfa.afreeplace.utils.PhotoBuilderTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    int categryId;

    @BeforeEach
    public void before() {
        categryId = CategoryBuilderTest.create(categoryRepository, "Catégorie 1").getId();
    }

    @AfterEach
    public void after() {
        CategoryBuilderTest.delete(categryId, categoryRepository);
        assertNull(categoryRepository.findById(categryId).orElse(null));
    }

    @Test
    public void ok() {
        assertNotNull(categoryRepository);
    }


}
