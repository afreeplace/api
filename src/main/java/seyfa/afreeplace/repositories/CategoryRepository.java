package seyfa.afreeplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seyfa.afreeplace.entities.business.Category;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
