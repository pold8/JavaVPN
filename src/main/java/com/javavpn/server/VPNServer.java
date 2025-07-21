package com.javavpn.server;

import com.javavpn.config.VPNConfig;
import com.javavpn.crypto.EncryptionHandler;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VPNServer {
    private final VPNConfig config;

    public VPNServer(VPNConfig config) {
        this.config = config;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(config.port)) {
            System.out.println("VPN server started on port " + config.port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                InputStream in = clientSocket.getInputStream();
                byte[] buffer = new byte[1024];
                int length = in.read(buffer);

                byte[] received = new byte[length];
                System.arraycopy(buffer, 0, received, 0, length);

                byte[] decrypted = EncryptionHandler.decrypt(received, config.encryptionKey);
                System.out.println("Decrypted message: " + new String(decrypted));

                // Respond, route, etc.
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
