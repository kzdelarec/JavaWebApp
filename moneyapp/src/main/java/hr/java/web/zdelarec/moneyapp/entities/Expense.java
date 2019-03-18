package hr.java.web.zdelarec.moneyapp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hr.java.web.zdelarec.moneyapp.enumeration.Type;

@Entity
@Table(name="EXPENSE")
public class Expense implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6877244455484064860L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	@NotEmpty(message = "{msg.valid.name.notEmpty}")
	@Size(min = 3, max = 50, message = "{msg.valid.name.size}")
	private String name;
	
	@Column(name="price")
	@NotNull(message = "{msg.valid.price.notNull}")
	@DecimalMin(value = "10", message = "{msg.valid.price.decimalMin}")
	private BigDecimal value;
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{msg.valid.type.notNull}")
	private Type xy;
	
	@Column(name="createdate")
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name="walletid")
	private Long walletId;
	
	@Column(name="expensetype")
	private String type;
	
	public String convertDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
		return dateFormat.format(date);
	}
	
	/*-------------------*/
	/*Getters and setters*/
	/*-------------------*/
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Type getXy() {
		return xy;
	}
	public void setXy(Type xy) {
		this.xy = xy;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getWalletId() {
		return walletId;
	}
	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}	
