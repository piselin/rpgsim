package actorBench;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import actorBench.Greeter.*;

import java.io.IOException;

public class HelloActorWorld {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("helloakka");

        try {
            final ActorRef printerActor = system.actorOf(Printer.props(), "printerActor");
            final ActorRef howdyGreeter = system.actorOf(Greeter.props("HOWDY", printerActor), "howdyGreeter");

            final ActorRef swissGreeter = system.actorOf(Greeter.props("Hoi", printerActor), "swissGreeter");

            howdyGreeter.tell(new Greeter.WhoToGreet("Akka"), ActorRef.noSender());
            howdyGreeter.tell(new Greet(), ActorRef.noSender());

            swissGreeter.tell(new Greeter.WhoToGreet("dooes this even exist?"), ActorRef.noSender());
            swissGreeter.tell(new Greet(), ActorRef.noSender());


            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();

        } catch (IOException ioe) {

        } finally {
            system.terminate();
        }
    }
}
