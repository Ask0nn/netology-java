package com.ask0n;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 62000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started!");
        while (true) {
            try (Socket socket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                System.out.println("New connection");
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.equals("end")) break;

                    long catalanNumber = new Catalan(Integer.parseInt(line)).getNumber();
                    out.println(String.format("Server: %s Catalan number: %s", line, catalanNumber));
                }
                if (line != null && line.equals("end")) break;
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
