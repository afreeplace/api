package seyfa.afreeplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seyfa.afreeplace.entities.Trade;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TradeRepository extends JpaRepository<Trade, Integer> {



}
