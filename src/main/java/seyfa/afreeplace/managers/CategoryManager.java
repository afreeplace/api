package seyfa.afreeplace.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seyfa.afreeplace.entities.business.Category;
import seyfa.afreeplace.repositories.CategoryRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class CategoryManager {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void generate() {
        Category category1 = new Category();
        category1.setName("category1");

        Category category2 = new Category();
        category2.setName("category2");

        Category category3 = new Category();
        category3.setName("category3");

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
    }

}
