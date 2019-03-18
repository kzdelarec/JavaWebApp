package hr.java.web.zdelarec.moneyapp.controllers;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hr.java.web.zdelarec.moneyapp.database.ExpenseRepository;
import hr.java.web.zdelarec.moneyapp.database.WalletRepository;
import hr.java.web.zdelarec.moneyapp.entities.Expense;
import hr.java.web.zdelarec.moneyapp.entities.Wallet;
import hr.java.web.zdelarec.moneyapp.enumeration.Type;

@Controller
public class TransactionController {
	
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(ExpenseController.class);
	
	@Autowired
	ExpenseRepository eRep;
	
	@Autowired
	WalletRepository wRep;
	
	
	/*---------------*/
	/*----Routing----*/
	/*---------------*/
	
	@PostMapping("transaction/new")
	public String showExpenseInfo(@Validated Expense transaction, Errors errors, Model model) {
		if(errors.hasErrors()) {
			model.addAttribute("type", Type.values());
			model.addAttribute("transaction", transaction);
			logger.info("Form was completed with errors. Redirecting back to form");
			return "transaction";
		} else {
			
			Date date = new Date();
			transaction.setDate(date);
			transaction.setType("transaction");
			
			Wallet wallet = getWalletFromDB();
			ArrayList<Expense> trans = getExpensesFromDB(wallet, transaction.getType());
			transaction = saveExpense(transaction, wallet);
			
			ArrayList<Expense> exps = getExpensesFromDB(wallet, "expense");
			wallet.setExpenses(exps);
			
			addExpensesToWallet(transaction, wallet, trans);
			
			model.addAttribute("transaction", transaction);
			model.addAttribute("wallet", wallet);
			return "transactionInfo";
		}
	}
	
	@GetMapping("transaction/new")
	public String showExpense(Model model) {
		model.addAttribute("transaction", new Expense());
		model.addAttribute("type", Type.values());
		return "transaction";
	}
	
	/*---------------*/
	/*----Methods----*/
	/*---------------*/
	
	private String getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) auth.getPrincipal();
		return user.getUsername();
	}
	
	private Expense saveExpense(Expense transaction, Wallet wallet) {
		transaction.setWalletId(wallet.getId());
		transaction = eRep.save(transaction);
		logger.info("Expense " + transaction.getName() + " saved to database");
		return transaction;
	}
	
	private void addExpensesToWallet(Expense transaction, Wallet wallet, ArrayList<Expense> trans) {
			trans.add(transaction);
			wallet.setTransactions(trans);
			logger.info(transaction.getName() + "expense was added to wallet");
	}
	
	private ArrayList<Expense> getExpensesFromDB(Wallet wallet, String type) {
		Iterable<Expense> expenseIterator = eRep.findByWalletIdAndType(wallet.getId(), type);
		ArrayList<Expense> exp = new ArrayList<>();
		for(Expense ex : expenseIterator) {
			exp.add(ex);
		}
		ArrayList<Expense> exps = exp;
		logger.info("Expenses retrieved from database");
		return exps;
	}

	private Wallet getWalletFromDB() {
		Iterable<Wallet> walletIterator = wRep.findByUsername(getUser());
		ArrayList<Wallet> userWallets = new ArrayList<>();
		for(Wallet wl : walletIterator) {
			userWallets.add(wl);
		}
		Wallet wallet = userWallets.get(0);
		logger.info("Wallet retrieved from database");
		return wallet;
	}
	
}
