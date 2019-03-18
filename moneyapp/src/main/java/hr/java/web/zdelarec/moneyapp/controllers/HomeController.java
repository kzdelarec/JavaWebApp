package hr.java.web.zdelarec.moneyapp.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hr.java.web.zdelarec.moneyapp.database.ExpenseRepository;
import hr.java.web.zdelarec.moneyapp.database.WalletRepository;
import hr.java.web.zdelarec.moneyapp.entities.Expense;
import hr.java.web.zdelarec.moneyapp.entities.Wallet;
import hr.java.web.zdelarec.moneyapp.enumeration.Type;

@Controller
public class HomeController {
	
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(ExpenseController.class);
	
	@Autowired
	ExpenseRepository eRep;
	
	@Autowired
	WalletRepository wRep;
	
	/*---------------*/
	/*----Routing----*/
	/*---------------*/
	
	@GetMapping(value="/home")
	public String showIndex(Model model) {
		Wallet wallet = getWalletFromDB();
		ArrayList<Expense> allExps = getAllExpensesFromDB(wallet);
		
		ArrayList<Expense> exps = getExpensesFromDB(wallet, "expense");
		ArrayList<Expense> trans = getExpensesFromDB(wallet, "transaction");
		wallet.setTransactions(trans);
		wallet.setExpenses(exps);
		
		model.addAttribute("expenses", allExps);
		model.addAttribute("total", wallet.calculateWallet());
		model.addAttribute("searchName", new String());
		model.addAttribute("type", Type.values());
		return "index";
	}
	
	@PostMapping(value="/home")
	public String showFiltered( @ModelAttribute(value="searchName")String searchName, @ModelAttribute(value="vrsta")String type, @ModelAttribute(value="searchDate") String searchDate, Model model) {
		Wallet wallet = getWalletFromDB();
		ArrayList<Expense> allExps = new ArrayList<>();
		
		if(Strings.isNullOrEmpty(searchName) && Strings.isNullOrEmpty(type) && Strings.isNullOrEmpty(searchDate)) {
			allExps = getAllExpensesFromDB(wallet);
		} else if(Strings.isNullOrEmpty(type) && !Strings.isNullOrEmpty(searchName) && Strings.isNullOrEmpty(searchDate) ) {
			allExps = getAllExpensesFromDBbyName(wallet, searchName);
		} else if(!Strings.isNullOrEmpty(type) && Strings.isNullOrEmpty(searchName) && Strings.isNullOrEmpty(searchDate) ){
			for(Type ty : Type.values()) {
				if (ty.name().equals(type)){
					allExps = getAllExpensesFromDBbyType(wallet, ty);
				}
			}
		} else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date = format.parse(searchDate);
				System.out.println(date);
				allExps = getAllExpensesFromDBbyDate(wallet, date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		ArrayList<Expense> exps = getExpensesFromDB(wallet, "expense");
		ArrayList<Expense> trans = getExpensesFromDB(wallet, "transaction");
		wallet.setTransactions(trans);
		wallet.setExpenses(exps);
		
		model.addAttribute("expenses", allExps);
		model.addAttribute("total", wallet.calculateWallet());
		model.addAttribute("searchName", new String());
		model.addAttribute("type", Type.values());
		return "index";
	}
	
	/*---------------*/
	/*----Methods----*/
	/*---------------*/
	
	private String getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) auth.getPrincipal();
		return user.getUsername();
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
	
	
	private ArrayList<Expense> getAllExpensesFromDB(Wallet wallet) {
		Iterable<Expense> expenseIterator = eRep.findByWalletId(wallet.getId());
		ArrayList<Expense> exp = new ArrayList<>();
		for(Expense ex : expenseIterator) {
			exp.add(ex);
		}
		ArrayList<Expense> exps = exp;
		logger.info("Expenses retrieved from database");
		return exps;
	}
	
	private ArrayList<Expense> getAllExpensesFromDBbyName(Wallet wallet, String name) {
		Iterable<Expense> expenseIterator = eRep.findByWalletIdAndNameIgnoreCase(wallet.getId(), name);
		ArrayList<Expense> exp = new ArrayList<>();
		for(Expense ex : expenseIterator) {
			exp.add(ex);
		}
		ArrayList<Expense> exps = exp;
		logger.info("Expenses retrieved from database");
		return exps;
	}
	
	private ArrayList<Expense> getAllExpensesFromDBbyType(Wallet wallet, Type type) {
		Iterable<Expense> expenseIterator = eRep.findByWalletIdAndXy(wallet.getId(), type);
		ArrayList<Expense> exp = new ArrayList<>();
		for(Expense ex : expenseIterator) {
			exp.add(ex);
		}
		ArrayList<Expense> exps = exp;
		logger.info("Expenses retrieved from database");
		return exps;
	}
	
	private ArrayList<Expense> getAllExpensesFromDBbyDate(Wallet wallet, Date date) {
		Iterable<Expense> expenseIterator = eRep.findByWalletIdAndDate(wallet.getId(), date);
		ArrayList<Expense> exp = new ArrayList<>();
		for(Expense ex : expenseIterator) {
			exp.add(ex);
		}
		ArrayList<Expense> exps = exp;
		logger.info("Expenses retrieved from database");
		return exps;
	}
}
