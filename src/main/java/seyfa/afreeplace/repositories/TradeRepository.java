package seyfa.afreeplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import seyfa.afreeplace.entities.business.Trade;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    @Query("SELECT trade" +
            " FROM Trade trade" +
            " WHERE trade.id = :tradeId" +
            " AND trade.owner.id = :userId")
    Optional<Trade> findTradeByOwnerId(int userId, int tradeId);

    List<Trade> findByOwner(int ownerId);
}
