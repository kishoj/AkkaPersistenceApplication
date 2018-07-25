package com.akka.persistence.events;

import java.io.Serializable;
import java.math.BigDecimal;

public class MoneyDeposited implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2011488881258695646L;

	private BigDecimal amount;

	public MoneyDeposited(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "MoneyDeposited [amount=" + amount + "]";
	}

}
