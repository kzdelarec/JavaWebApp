package hr.java.web.zdelarec.moneyapp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="Wallet")
public class Wallet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4416054162653954564L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	@Column(name="name")
	private String walletName;
	
	@Column(name="createdate")
	@JsonIgnore
	private Date createDate;
	
	@Column(name="username")
	private String username;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Expense.class, fetch = FetchType.LAZY)
	@JoinTable(name="expense", joinColumns = @JoinColumn(name="walletid"))
	private List<Expense> expenses;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,targetEntity = Expense.class, fetch = FetchType.LAZY)
	@JoinTable(name="expense", joinColumns = @JoinColumn(name="walletid"))
	private List<Expense> transactions;
	
	public Wallet() {}
	
	public String convertDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
		return dateFormat.format(createDate);
	}
	
	public List<Expense> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Expense> transactions) {
		this.transactions = transactions;
	}
	public String getName() {
		return walletName;
	}
	public void setName(String walletName) {
		this.walletName = walletName;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}
	
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	
	public String calculateWallet() {
		BigDecimal sum = new BigDecimal(0);
		if(expenses != null) {
			for(Expense exp : expenses) {
				if(transactions.isEmpty() && exp.getType().equals("transaction")) {
					transactions.add(exp);
					continue;
				} else {
					sum=sum.add(exp.getValue());
				}
				
				//System.out.println(exp.getValue());
			}
			sum = sum.multiply(new BigDecimal(-1));
		}
		
		if(transactions != null) {
			for(Expense trn : transactions) {
				sum=sum.add(trn.getValue());
				//System.out.println(trn.getValue());
			}
		}
		
		
		return sum.toString();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date date) {
		this.createDate = date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
