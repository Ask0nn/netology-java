package com.ask0n;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ThreadSocket implements Runnable {
    private final Socket socket;

    public ThreadSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final var out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            final var requestLine = in.readLine();
            final var parts = requestLine.split(" ");

            if (parts.length != 3) return;

            final var path = parts[1];
            if (!Server.validPaths.contains(path)){
                Server.notFound(out);
                out.flush();
                return;
            }

            final var filePath = Path.of(".", "public", path);
            final var mimeType = Files.probeContentType(filePath);

            if (path.equals("/classic.html")){
                Server.classic(out, mimeType, filePath);
                out.flush();
                return;
            }

            final var length = Files.size(filePath);
            Server.ok(out, mimeType, length);
            Files.copy(filePath, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
