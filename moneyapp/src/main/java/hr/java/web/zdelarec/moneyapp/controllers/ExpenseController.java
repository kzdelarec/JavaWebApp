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
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;

import hr.java.web.zdelarec.moneyapp.database.ExpenseRepository;
import hr.java.web.zdelarec.moneyapp.database.WalletRepository;
import hr.java.web.zdelarec.moneyapp.entities.Expense;
import hr.java.web.zdelarec.moneyapp.entities.Wallet;
import hr.java.web.zdelarec.moneyapp.enumeration.Type;

@Controller
public class ExpenseController{
	
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(ExpenseController.class);
	
	@Autowired
	ExpenseRepository eRep;
	
	@Autowired
	WalletRepository wRep;
	
	/*---------------*/
	/*----Routing----*/
	/*---------------*/
	
	@PostMapping("expense/new")
	public String showInfo(@Validated Expense expense, Errors errors, Model model) {
		if(errors.hasErrors()) {
			model.addAttribute("type", Type.values());
			logger.info("Form was completed with errors. Redirecting back to form");
			return "expense";
		} else {
		
			Date date = new Date();
			expense.setDate(date);
			expense.setType("expense");
			
			Wallet wallet = getWalletFromDB();
			ArrayList<Expense> exps = getExpensesFromDB(wallet, expense.getType());
			
			expense = saveExpense(expense, wallet);
			
			ArrayList<Expense> trans = getExpensesFromDB(wallet, "transaction");
			wallet.setTransactions(trans);
			addExpenseToWallet(expense, wallet, exps);
			
			model.addAttribute("expense", expense);
			model.addAttribute("wallet", wallet);
			return "expenseInfo";
		}
	}

	@GetMapping("expense/new")
	public String showExpense(Model model) {
		model.addAttribute("expense", new Expense());
		model.addAttribute("type", Type.values());
		return "expense";
	}
	
	@GetMapping("resetWallet")
	public String resetWallet(Model model) {
		Wallet wallet = getWalletFromDB();
		wRep.resetWallet(wallet.getId());
		logger.info("Session has been completed");
		return "redirect:/home";
	}
	@GetMapping(value="resetwalletbyusername")
	public String resetWalletByUsername(Model model, @RequestParam Long id) {
		wRep.resetWallet(id);
		logger.info("Session has been completed");
		return "redirect:/home";
	}
	
	/*---------------*/
	/*----Methods----*/
	/*---------------*/
	
	private String getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) auth.getPrincipal();
		return user.getUsername();
	}
	
	private Expense saveExpense(Expense expense, Wallet wallet) {
		expense.setWalletId(wallet.getId());
		expense = eRep.save(expense);
		logger.info("Expense " + expense.getName() + " saved to database");
		return expense;
	}

	private void addExpenseToWallet(Expense expense, Wallet wallet, ArrayList<Expense> exps) {
			exps.add(expense);
			wallet.setExpenses(exps);
			logger.info(expense.getName() + "expense was added to wallet");
		
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
