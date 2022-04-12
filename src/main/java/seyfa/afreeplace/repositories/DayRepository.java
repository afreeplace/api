package seyfa.afreeplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.ScheduleDay;


@Repository
@Transactional
public interface DayRepository extends JpaRepository<ScheduleDay, Integer> {


}
