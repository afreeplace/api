package seyfa.afreeplace.repositories;

import seyfa.afreeplace.entities.business.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * find user by given email
     */
    Optional<User> findByEmail(String email);

}
