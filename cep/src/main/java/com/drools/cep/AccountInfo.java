/**
 * 
 */
package com.drools.cep;

public class AccountInfo {
	
	private String accountNumber = "";
	private double aveOfLastClaims = 0.00;
	private double aveForPeriod = 0.00;
	private boolean eligibleForBonusClaims = false;
	private boolean eligibleForPeriodicBonus = false;
	
	public AccountInfo() {
		super();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAveOfLastClaims() {
		return aveOfLastClaims;
	}

	public void setAveOfLastClaims(double aveOfLastClaims) {
		this.aveOfLastClaims = aveOfLastClaims;
	}

	public double getAveForPeriod() {
		return aveForPeriod;
	}

	public void setAveForPeriod(double aveForPeriod) {
		this.aveForPeriod = aveForPeriod;
	}

	public boolean isEligibleForBonusClaims() {
		return eligibleForBonusClaims;
	}

	public void setEligibleForBonusClaims(boolean eligibleForBonusClaims) {
		this.eligibleForBonusClaims = eligibleForBonusClaims;
	}

	public boolean isEligibleForPeriodicBonus() {
		return eligibleForPeriodicBonus;
	}

	public void setEligibleForPeriodicBonus(boolean eligibleForPeriodicBonus) {
		this.eligibleForPeriodicBonus = eligibleForPeriodicBonus;
	}

}