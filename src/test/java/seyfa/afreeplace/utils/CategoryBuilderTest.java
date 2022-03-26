package seyfa.afreeplace.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.entities.business.Category;
import seyfa.afreeplace.repositories.CategoryRepository;

public class CategoryBuilderTest {

    static final Logger logger = LoggerFactory.getLogger(CategoryBuilderTest.class);

    public static Category create(CategoryRepository categoryRepository, String name) {
        Category category = new Category();
        category.setName(name);

        if(categoryRepository != null) {
            categoryRepository.save(category);
            logger.info("Created category {} " + category);
        }
        return category;
    }

    public static void delete(int categoryId, CategoryRepository categoryRepository) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category != null) {
            categoryRepository.delete(categoryRepository.findById(categoryId).get());
            logger.info("Deleted category {}", categoryId);
        } else {
            logger.info("Already deleted category {}", categoryId);
        }
    }

}
