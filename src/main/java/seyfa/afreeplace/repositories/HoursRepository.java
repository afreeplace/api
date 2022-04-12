package seyfa.afreeplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seyfa.afreeplace.entities.business.Hours;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface HoursRepository extends JpaRepository<Hours, Integer> {
}
