package hr.java.web.zdelarec.moneyapp.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hr.java.web.zdelarec.moneyapp.database.ExpenseRepository;
import hr.java.web.zdelarec.moneyapp.database.WalletRepository;
import hr.java.web.zdelarec.moneyapp.entities.Expense;
import hr.java.web.zdelarec.moneyapp.entities.Wallet;

@Controller
public class AllExpensesController {
	
	@Autowired
	ExpenseRepository eRep;
	
	@Autowired
	WalletRepository wRep;
	
	@GetMapping(value="/allExpenses")
	public String showIndex(Model model) {
		Iterable<Wallet> iterator2 = wRep.findAll();
		ArrayList<Wallet> list2 = new ArrayList<>();
		for(Wallet wl : iterator2) {
			Iterable<Expense> iterator = eRep.findByWalletId(wl.getId());
			ArrayList<Expense> list = new ArrayList<>();
			for(Expense ex : iterator) {
				list.add(ex);
			}
			wl.setExpenses(list);
			wl.setTransactions(new ArrayList<>());
			list2.add(wl);
		}
		
		
		
		model.addAttribute("wallets", list2);
		//model.addAttribute("expenses", list);
		
		return "allExpenses";
	}

}
