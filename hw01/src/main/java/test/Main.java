package test;

import hr.fer.oprpp2.shared.*;

public class Main {
    public static void main(String[] args) {
        Message m = new HelloMessage(2, "Foo Bar", 123123);
//        Message m = new AckMessage(2, 123);
//        Message m = new ByeMessage(2, 123);
//        Message m = new InMessage(2, "Foo Bar", "Message");
//        Message m = new OutMessage(2, 123, "Message");
        System.out.println(m);

        byte[] serialized = m.serialize();
        Message deserialized = Message.deserializeRaw(serialized);

        if (deserialized == null) {
            System.out.println("Deserialization failed");
        } else {
            System.out.println(deserialized);
        }
    }
}
