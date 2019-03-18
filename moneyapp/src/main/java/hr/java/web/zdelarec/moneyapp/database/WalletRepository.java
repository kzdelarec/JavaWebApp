package hr.java.web.zdelarec.moneyapp.database;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.java.web.zdelarec.moneyapp.entities.Wallet;

public interface WalletRepository extends CrudRepository<Wallet, Long>{
	
	Iterable<Wallet> findByUsername(String username);
	@Transactional
	@Modifying
	@Query("DELETE FROM Expense e WHERE e.walletId = :id")
	void resetWallet(@Param("id") Long id);
	
	/*Iterable<Wallet> findAll();
	Wallet save(Wallet wallet);
	Wallet findone(Long id);
	Iterable<Wallet> findByUser(String User);
	void resetWallet(Long id);
	void deleteWallet(Long id);
	*/
}
