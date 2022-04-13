package seyfa.afreeplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seyfa.afreeplace.entities.business.Rate;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RateRepository extends JpaRepository<Rate, Integer> {

    @Query("SELECT rate" +
            " FROM Rate rate" +
            " WHERE rate.user.id = :userId" +
            " AND rate.trade.id = :tradeId")
    Optional<Rate> findByUserAndTrade(int userId, int tradeId);

    @Query("SELECT COUNT(rate)" +
            " FROM Rate rate" +
            " WHERE rate.user.id = :userId" +
            " AND rate.trade.id = :tradeId")
    int hasAnyComment(int userId, int tradeId);

}
