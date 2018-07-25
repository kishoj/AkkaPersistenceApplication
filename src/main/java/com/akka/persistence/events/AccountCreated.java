package com.akka.persistence.events;

import java.io.Serializable;

public class AccountCreated  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2962494667385976277L;
	
	private String accountNumber;
	private String accountName;
	
	public AccountCreated(String accountNumber, String accountName) {
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
		return "AccountCreated [accountNumber=" + accountNumber + ", accountName=" + accountName + "]";
	}
	
}