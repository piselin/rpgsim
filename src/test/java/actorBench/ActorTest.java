package actorBench;

import actorBench.Greeter.Greet;
import actorBench.Greeter.WhoToGreet;
import actorBench.Printer.Greeting;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActorTest {
    static ActorSystem system;

    @BeforeAll
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterAll
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final TestKit testProbe = new TestKit(system);
        final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));

        helloGreeter.tell(new WhoToGreet("Akkas"), ActorRef.noSender());
        helloGreeter.tell(new Greet(), ActorRef.noSender());

        Greeting greeting = testProbe.expectMsgClass(Greeting.class);
        assertEquals("Hello, Akka", greeting.message);
    }
}




