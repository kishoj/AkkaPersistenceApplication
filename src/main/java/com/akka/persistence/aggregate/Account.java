package com.akka.persistence.aggregate;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akka.persistence.commands.CreateAccount;
import com.akka.persistence.commands.DepositMoney;
import com.akka.persistence.commands.TakeSnapshot;
import com.akka.persistence.commands.WithdrawMoney;
import com.akka.persistence.events.AccountCreated;
import com.akka.persistence.events.MoneyDeposited;
import com.akka.persistence.events.MoneyWithdrawn;

import akka.persistence.AbstractPersistentActor;
import akka.persistence.RecoveryCompleted;

public class Account extends AbstractPersistentActor {
	private static final Logger LOG = LoggerFactory.getLogger(Account.class);
    	
	private String accountId;
	private String accountName;
	
	private BigDecimal balance = BigDecimal.ZERO;
	
	private AccountState state = new AccountState();
	
	public Account(String accountId, String accountName) {
		this.balance = BigDecimal.ZERO;
		this.accountId = accountId;
		this.accountName = accountName;
	}
	
	@Override
	public String persistenceId() {
		return accountId;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
			.match(CreateAccount.class, command -> {
				LOG.info("New Account Created");
				persist(new AccountCreated(command.getAccountNumber(), command.getAccountName()), this::applyEvent);
			})
			.match(DepositMoney.class, command -> {
				LOG.info("Deposit " + command.getAmount());
				persist(new MoneyDeposited(command.getAmount()), this::applyEvent);
			})
			.match(WithdrawMoney.class, command -> {
				LOG.info("WithDraw " + command.getAmount());
				persist(new MoneyWithdrawn(command.getAmount()), this::applyEvent);
			})
			.match(TakeSnapshot.class, event -> {
				LOG.info("TakeSnapshot ");
				saveSnapshot(state.copy());
			})
			.matchEquals("print", command -> {
				System.out.println(toString());
			}).build();
	}

	@Override
	public Receive createReceiveRecover() {
		return receiveBuilder().match(AccountCreated.class, event -> {
			state.update(event);
			this.balance = BigDecimal.ZERO;
			this.accountId = event.getAccountNumber();
			this.accountName = event.getAccountName();
			System.out.println("Replay Event AccountCreated. " + toString());
		}).match(MoneyDeposited.class, event -> {
			state.update(event);
			this.balance = balance.add(event.getAmount());
			System.out.println("Replay Event MoneyDeposited " + event.getAmount() + " " + toString());
		}).match(MoneyWithdrawn.class, event -> {
			state.update(event);
			this.balance = balance.subtract(event.getAmount());
			System.out.println("Replay Event MoneyWithdrawn " + event.getAmount() + " " + toString());
		}).match(RecoveryCompleted.class, e -> {
			System.out.println("Replay Events Completed!...");
		}).build();
	}

	private void applyEvent(Object event) {
		if (event instanceof AccountCreated) {
			this.state.update((AccountCreated) event);
			this.accountId = ((AccountCreated) event).getAccountNumber();
			this.accountName = ((AccountCreated) event).getAccountName();
			this.balance = BigDecimal.ZERO;
		}
		if (event instanceof MoneyDeposited) {
			this.state.update((MoneyDeposited) event);
			this.balance = balance.add(((MoneyDeposited) event).getAmount());
		}
		if (event instanceof MoneyWithdrawn) {
			this.state.update((MoneyWithdrawn) event);
			this.balance = balance.subtract(((MoneyWithdrawn) event).getAmount());
		}
		getContext().getSystem().eventStream().publish(event);		
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountName=" + accountName + ", balance=" + balance + ", state="
				+ state + "]";
	}
	
}
