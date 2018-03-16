package actorBench;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class Greeter extends AbstractActor {
    private final ActorRef printerActor;
    private final String message;
    private String greeting = "";

    static public Props props(final String message, final ActorRef printerActor) {
        return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    }

    static public class WhoToGreet {
        public final String who;

        public WhoToGreet(String who) {
            this.who = who;

        }
    }

    static public class Greet {
        public Greet() {

        }
    }

    public Greeter(String message, ActorRef printerActor) {
        this.message = message;
        this.printerActor = printerActor;
    }


    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(WhoToGreet.class, wtg -> this.greeting = message + ", " + wtg.who)
                .match(Greet.class, x -> printerActor.tell(new Printer.Greeting(greeting), getSelf()))
                .build();
    }
}
