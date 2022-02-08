package com.ask0n;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 25788;
    private static final int DELAY = 2000; //2s

    private static InetSocketAddress socketAddress = new InetSocketAddress(HOSTNAME, PORT);

    public static void main(String[] args) throws IOException {
        final SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);

        try {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10); // 1024

            while(true){
                String msg = read("Enter words with space");
                if (msg.equals("end")) break;

                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));

                Thread.sleep(DELAY);

                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(
                        new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        }
        catch (IOException | InterruptedException e){
            e.printStackTrace(System.err);
        }
    }

    public static String read(String msg) throws IOException {
        if (msg != null) System.out.println(msg);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        return in.readLine();
    }
}
