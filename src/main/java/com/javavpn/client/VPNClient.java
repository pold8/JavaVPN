package com.javavpn.client;

import com.javavpn.config.VPNConfig;
import com.javavpn.crypto.EncryptionHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class VPNClient {
    private final VPNConfig config;

    public VPNClient(VPNConfig config) {
        this.config = config;
    }

    public void connect() {
        try (Socket socket = new Socket(config.serverIp, config.port)) {
            System.out.println("Connected to VPN server.");

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            String msg = "Hello VPN Server!";
            byte[] encrypted = EncryptionHandler.encrypt(msg.getBytes(), config.encryptionKey);
            out.write(encrypted);
            out.flush();

            // Handle response...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
