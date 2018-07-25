package com.akka.persistence;

import java.math.BigDecimal;

import com.akka.persistence.aggregate.Account;
import com.akka.persistence.commands.CreateAccount;
import com.akka.persistence.commands.DepositMoney;
import com.akka.persistence.commands.TakeSnapshot;
import com.akka.persistence.commands.WithdrawMoney;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class AkkaPersistenceApplicationMain {

	private static final String ACCOUNT_NUMBER = "ACC1234567890";
	private static final String ACCOUNT_NAME = "KISHOJ BAJRACHARYA";

	public static void main(String[] args) {

		ActorSystem system = ActorSystem.create("BankAccountSystem");
		
		final ActorRef persistentActor = system
				.actorOf(Props.create(Account.class, () -> new Account(ACCOUNT_NUMBER, ACCOUNT_NAME)));
		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new CreateAccount(ACCOUNT_NUMBER, ACCOUNT_NAME), ActorRef.noSender());
		persistentActor.tell(new TakeSnapshot(), ActorRef.noSender());

		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new DepositMoney(BigDecimal.ONE), ActorRef.noSender());
		persistentActor.tell(new TakeSnapshot(), ActorRef.noSender());
		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new DepositMoney(BigDecimal.TEN), ActorRef.noSender());
		persistentActor.tell(new TakeSnapshot(), ActorRef.noSender());
		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new WithdrawMoney(BigDecimal.ONE), ActorRef.noSender());
		persistentActor.tell(new TakeSnapshot(), ActorRef.noSender());
		persistentActor.tell("print", ActorRef.noSender());
	}
}
