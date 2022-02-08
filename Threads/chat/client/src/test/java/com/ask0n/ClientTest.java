package com.ask0n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ClientTest {

    @Test
    public void testConnection() throws IOException {
        String username = "Ask0n";
        Client client = new Client(username);

        String receivedMessage = client.getInput().readLine();

        Assertions.assertTrue(receivedMessage.contains(username));
    }

    @Test
    public void testSendAndReceiveMessages() throws IOException {
        Client client = new Client("Ask0n");
        String message = "test messages";

        client.sendMessage(message);
        String receivedMessage = client.getInput().lines().skip(1).findFirst().get();
        Assertions.assertTrue(receivedMessage.contains(message));
    }
}
