package com.akka.persistence.commands;

import java.math.BigDecimal;

public class DepositMoney {
	
	private BigDecimal amount;
	
	public DepositMoney(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "DepositMoney [amount=" + amount + "]";
	}	
	
}