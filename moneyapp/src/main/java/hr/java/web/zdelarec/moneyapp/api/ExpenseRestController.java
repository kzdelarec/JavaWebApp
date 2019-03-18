package hr.java.web.zdelarec.moneyapp.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hr.java.web.zdelarec.moneyapp.database.ExpenseRepository;
import hr.java.web.zdelarec.moneyapp.database.WalletRepository;
import hr.java.web.zdelarec.moneyapp.entities.Expense;

@RestController
@Secured("ROLE_USER")
@RequestMapping(path="/api/expense", produces="application/json")
@CrossOrigin
public class ExpenseRestController {
	
	@Autowired
	ExpenseRepository eRep;
	
	@Autowired
	WalletRepository wRep;
	

	@GetMapping
	public Iterable<Expense> findAll(){
		return eRep.findAll();
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Expense> findOne(@PathVariable Long id) { 
		Expense expense =  eRep.findById(id).orElse(null);
		if(expense != null) {
			return new ResponseEntity<>(expense,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseStatus(HttpStatus.CREATED) 
	@PostMapping(consumes="application/json")
	@Secured("ROLE_USER")
	public Expense save( @RequestBody Expense expense) {
		return eRep.save(expense);
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	@DeleteMapping("/{id}") 
	public void delete(@PathVariable Long id) { 
		Expense expense =  eRep.findById(id).orElse(null);
		eRep.delete(expense);
		
	}

}
