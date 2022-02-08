package com.ask0n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ServerTest {
    @Test
    public void testConnection() throws InterruptedException {
        Server server = new Server();
        server.start();
        String username = "Ask0n";

        Client client = new Client(username);
        Thread.sleep(2000);
        var users = server.getUsers();

        Assertions.assertEquals(username, users.get(0).getUsername());
    }

    @Test
    public void testSendToAllMessages() throws InterruptedException {
        Server server = new Server();
        server.start();
        String testMessage = "Test Message";

        List<Client> clients =
                Arrays.asList(new Client("Test1"), new Client("Test2"), new Client("Test3"));

        Thread.sleep(3000);
        server.sendMessageToAll(testMessage);
        Thread.sleep(5000);

        for (Client client : clients){
            var messagesStream = client.getInput().lines();
            Assertions.assertTrue(messagesStream.anyMatch(x -> x.equals(testMessage)));
        }
    }
}
