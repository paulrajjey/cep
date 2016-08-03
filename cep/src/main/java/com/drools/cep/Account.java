/**
 * 
 */
package com.drools.cep;


public class Account {
	private String number = "";
	private double threshold = 0.00;
	
	public Account() {
		super();
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}	
}