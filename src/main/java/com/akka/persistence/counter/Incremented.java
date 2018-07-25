package com.akka.persistence.counter;

import java.io.Serializable;

public class Incremented implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8117398593682602382L;
	
	private int value;
	
	public Incremented(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
