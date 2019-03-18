package hr.java.web.zdelarec.moneyapp.database;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import hr.java.web.zdelarec.moneyapp.entities.Expense;
import hr.java.web.zdelarec.moneyapp.enumeration.Type;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
	
	Iterable<Expense> findByWalletIdAndType(Long id, String type);
	Iterable<Expense> findByWalletIdAndNameIgnoreCase(Long id, String name);
	Iterable<Expense> findByWalletIdAndXy(Long id, Type xy);
	Iterable<Expense> findByWalletId(Long id);
	Iterable<Expense> findByWalletIdAndDate(Long id, Date date);
	
	
	/*Iterable<Expense> findAll();
	Expense save(Expense expense);
	Expense findone(Long id);
	
	
	void deleteExpense(Long id);
	Expense update(Expense expense);*/
}
