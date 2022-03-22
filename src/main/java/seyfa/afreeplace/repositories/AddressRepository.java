package seyfa.afreeplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seyfa.afreeplace.entities.business.Address;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Integer> {



}
