package com.example.model;

/**
 * @author Tuhin Gupta
 *
 */
/*
 * One sample JSON record - {     "CustomerType":1240,"AccountNumber":6357788931004,"AccountBalance":164410,"CustomerName":"A"}, 
 */
public class Customer {
	
	String CustomerType;
	String AccountNumber;
	String AccountBalance;
	String CustomerName;
	
	
	public String getCustomerType() {
		return CustomerType;
	}
	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}
	public String getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}
	public String getAccountBalance() {
		return AccountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		AccountBalance = accountBalance;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	
	
	

}
