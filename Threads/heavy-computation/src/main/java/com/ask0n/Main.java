package com.ask0n;

import java.io.*;
import java.net.Socket;

public class Main {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 62000;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOSTNAME, PORT);

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String msg = read("Enter 'n' number of Catalan numbers (max 30)");
                out.println(msg);
                if (msg.equals("end")) break;
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(String msg) throws IOException {
        if (msg != null) System.out.println(msg);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        return in.readLine();
    }
}
