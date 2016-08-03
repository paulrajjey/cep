/**
 * 
 */
package com.drools.cep;

import java.io.Serializable;


public class ClaimApprovedEvent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String accountNumber = "";
	private double amount = 0.00;
	
	public ClaimApprovedEvent(String accountNumber, double amount) {
		this.accountNumber = accountNumber;
		this.amount = amount;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
