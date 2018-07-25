package com.akka.persistence.commands;

public class CreateAccount {	
	private String accountNumber;
	private String accountName;
	
	public CreateAccount(String accountNumber, String accountName) {
		this.accountNumber = accountNumber;
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	@Override
	public String toString() {
		return "CreateAccount [accountNumber=" + accountNumber + ", accountName=" + accountName + "]";
	}	
	
}
