package com.akka.persistence.counter;

import com.akka.persistence.commands.TakeSnapshot;

import akka.persistence.AbstractPersistentActor;
import akka.persistence.RecoveryCompleted;
import akka.persistence.SnapshotOffer;

public class CounterActor extends AbstractPersistentActor {

	private int count = 0;

	private CounterHistory counterHistory = new CounterHistory();
	private int snapShotInterval = 5;

	@Override
	public String persistenceId() {
		return "counter-id";
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Increment.class, command -> {
			System.out.println("Receive command to increment");
			persist(new Incremented(count), this::updateCounter);
		}).match(TakeSnapshot.class, event -> {
			saveSnapshot(counterHistory.copy());
		}).matchEquals("print", command -> {
			System.out.println("Value of count: " + count);
		}).build();
	}

	@Override
	public Receive createReceiveRecover() {
		return receiveBuilder().match(SnapshotOffer.class, ss -> {
			System.out.println("Recovery from snapshot: " + count);
			counterHistory = (CounterHistory) ss.snapshot();
			count = counterHistory.size();
			System.out.println("Count from snapshot: " + counterHistory.toString());
		}).match(Incremented.class, event -> {
			System.out.println("Recovery with a value of count: " + count);
			count += 1;
		}).match(RecoveryCompleted.class, e -> {
			System.out.println("Recovery Completed");
		}).build();
	}

	public int getCount() {
		return count;
	}

	private void updateCounter(Incremented event) {
		this.count += 1;
		counterHistory.update(event);
		getContext().getSystem().eventStream().publish(event);
		if (lastSequenceNr() % snapShotInterval == 0 && lastSequenceNr() != 0) {
			saveSnapshot(counterHistory.copy());
		}
	}
}
