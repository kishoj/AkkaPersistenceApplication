package com.akka.persistence;

import com.akka.persistence.counter.CounterActor;
import com.akka.persistence.counter.Increment;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class CounterTestApplicationMain {

	public static void main(String[] args) {
		
		ActorSystem system = ActorSystem.create("CounterSystem");

		final ActorRef persistentActor = system
				.actorOf(Props.create(CounterActor.class));
		persistentActor.tell("print", ActorRef.noSender());
		persistentActor.tell(new Increment(), ActorRef.noSender());
		persistentActor.tell(new Increment(), ActorRef.noSender());
		persistentActor.tell(new Increment(), ActorRef.noSender());
		persistentActor.tell(new Increment(), ActorRef.noSender());
		persistentActor.tell(new Increment(), ActorRef.noSender());
		persistentActor.tell("print", ActorRef.noSender());
	}

}
