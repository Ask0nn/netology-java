package com.ask0n;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 25788;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(HOSTNAME, PORT));

        while (true) {
            try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10); //1024

                while (socketChannel.isConnected()){
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) break;

                    final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    inputBuffer.clear();

                    System.out.println("Client: " + msg);

                    socketChannel.write(
                            ByteBuffer.wrap(
                                    ("Server: " + msg.replace(" ", "")).getBytes(StandardCharsets.UTF_8)));
                }
            }
            catch (IOException e){
                e.printStackTrace(System.err);
            }
        }
    }
}
