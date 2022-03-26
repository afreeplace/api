package seyfa.afreeplace.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seyfa.afreeplace.entities.business.Category;
import seyfa.afreeplace.repositories.CategoryRepository;
import seyfa.afreeplace.utils.CategoryBuilderTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryManagerTest {

    @Autowired
    CategoryManager categoryManager;

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
        assertNotNull(categoryManager);
    }

    @Test
    public void findAll() {
        List<Category> categories = categoryManager.findAll();
        assertEquals(3, categories.size());
    }
}
