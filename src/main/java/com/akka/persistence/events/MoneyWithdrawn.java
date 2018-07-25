package com.akka.persistence.events;

import java.io.Serializable;
import java.math.BigDecimal;

public class MoneyWithdrawn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1352856720817243815L;

	private BigDecimal amount;

	public MoneyWithdrawn(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "MoneyWithdrawn [amount=" + amount + "]";
	}

}
