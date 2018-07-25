package com.akka.persistence.counter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CounterHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1851765819142474108L;

	private final List<Incremented> events;

	public CounterHistory() {
		this(new ArrayList<>());
	}

	public CounterHistory(ArrayList<Incremented> events) {
		this.events = events;
	}

	public CounterHistory copy() {
		return new CounterHistory(new ArrayList<>(events));
	}

	public void update(Incremented event) {
		events.add(event);
	}

	public int size() {
		return events.size();
	}

	@Override
	public String toString() {
		return events.toString();
	}

}