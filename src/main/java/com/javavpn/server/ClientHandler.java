package com.javavpn.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            out.write("Welcome to JavaVPN server!\n");
            out.flush();

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received: " + line);
                out.write("Echo: " + line + "\n");
                out.flush();
            }

        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        }
    }
}
