package com.akka.persistence.commands;

import java.math.BigDecimal;

public class WithdrawMoney {
	
	private BigDecimal amount;
	
	public WithdrawMoney(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "WithdrawMoney [amount=" + amount + "]";
	}
	
}
