package hr.java.web.zdelarec.moneyapp.api;

import hr.java.web.zdelarec.moneyapp.database.ExpenseRepository;
import hr.java.web.zdelarec.moneyapp.database.WalletRepository;
import hr.java.web.zdelarec.moneyapp.entities.Wallet;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@Secured("ROLE_USER")
@RequestMapping(path="/api/wallet", produces="application/json")
@CrossOrigin
public class WalletRestController {
	
	@Autowired
	ExpenseRepository eRep;
	
	@Autowired
	WalletRepository wRep;
	
	@GetMapping
	public Iterable<Wallet> findAll(){
		Iterable<Wallet> iterator2 = wRep.findAll();
		ArrayList<Wallet> list2 = new ArrayList<>();
		for(Wallet wl : iterator2) {
			wl.setExpenses(Lists.newArrayList(eRep.findByWalletIdAndType(wl.getId(), "expense")));
			list2.add(wl);
		}
		return list2;
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Wallet> findOne(@PathVariable Long id) { 
		Wallet wallet =  wRep.findById(id).orElse(null);
		wallet.setExpenses(Lists.newArrayList(eRep.findByWalletIdAndType(wallet.getId(), "expense")));
		if(wallet != null) {
			return new ResponseEntity<>(wallet,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseStatus(HttpStatus.CREATED) 
	@PostMapping(path="/{id}", consumes="application/json")
	@Secured("ROLE_USER")
	public Wallet save( @RequestBody Wallet wallet) {
		wallet.setCreateDate(new Date());
		wallet.setExpenses(new ArrayList<>());
		wallet.setTransactions(new ArrayList<>());
		return wRep.save(wallet);
	}
	
	
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	@DeleteMapping("/{id}") 
	public void delete(@PathVariable Long id) { 
		Wallet wallet =  wRep.findById(id).orElse(null);
		wRep.delete(wallet);
	}

}
