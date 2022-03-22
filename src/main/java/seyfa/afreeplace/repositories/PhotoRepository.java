package seyfa.afreeplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import seyfa.afreeplace.entities.business.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
