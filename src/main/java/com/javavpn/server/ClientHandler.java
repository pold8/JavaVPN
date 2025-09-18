package com.javavpn.server;

import com.javavpn.network.PacketHandler;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())
        ) {
            out.writeUTF("Welcome to JavaVPN server!");

            while (true) {
                int length = in.readInt();
                byte[] encrypted = new byte[length];
                in.readFully(encrypted);

                // Decrypt request
                byte[] decrypted = PacketHandler.decrypt(encrypted);
                String request = new String(decrypted);

                System.out.println("Client wants: " + request);

                // === Simple HTTP GET forwarding ===
                URL url = new URL(request);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line).append("\n");
                }

                // Encrypt response
                byte[] encryptedResp = PacketHandler.encrypt(response.toString().getBytes());
                out.writeInt(encryptedResp.length);
                out.write(encryptedResp);
                out.flush();
            }

        } catch (Exception e) {
            System.err.println("Client disconnected: " + e.getMessage());
        }
    }
}
