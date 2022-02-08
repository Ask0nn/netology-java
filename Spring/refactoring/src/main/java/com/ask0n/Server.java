package com.ask0n;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int DEFAULT_PORT = 9999;
    public static final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png",
            "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html",
            "/events.html", "/events.js");

    private final ServerSocket serverSocket;
    private final int port;
    private final ExecutorService threadPoll = Executors.newFixedThreadPool(64);

    public Server() throws IOException {
        port = DEFAULT_PORT;
        this.serverSocket = new ServerSocket(port);
    }

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public void startServer() {
        System.out.println("Listening " + port);
        while (true) {
            try {
                threadPoll.submit(new ThreadSocket(serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public void processRequest(Socket socket){
        new Thread(new ThreadSocket(socket));
    }

    public static void notFound(BufferedOutputStream out) throws IOException {
        out.write((
                "HTTP/1.1 404 Not Found\r\n" +
                        "Content-Length: 0\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
    }
    public static void ok(BufferedOutputStream out, String mimeType, long length) throws IOException {
        out.write((
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + mimeType + "\r\n" +
                        "Content-Length: " + length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
    }
    public static void classic(BufferedOutputStream out, String mimeType, Path filePath) throws IOException {
        final var template = Files.readString(filePath);
        final var content = template.replace(
                "{time}",
                LocalDateTime.now().toString()
        ).getBytes();
        out.write((
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + mimeType + "\r\n" +
                        "Content-Length: " + content.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
        out.write(content);
    }
}
