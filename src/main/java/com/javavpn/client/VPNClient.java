package com.javavpn.client;

import com.javavpn.config.VPNConfig;
import com.javavpn.network.PacketHandler;
import com.javavpn.utils.LoggerUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class VPNClient {
    private static final Logger logger = LoggerUtil.createLogger(VPNClient.class);
    private final VPNConfig config;

    public VPNClient(VPNConfig config) {
        this.config = config;
    }

    public void connect() {
        try (Socket socket = new Socket(config.getHost(), config.getPort());
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            logger.info("Connected to VPN server.");
            System.out.println(in.readUTF()); // welcome

            while (true) {
                System.out.print("Enter URL (e.g. http://example.com): ");
                String url = scanner.nextLine();

                // Encrypt request
                byte[] encrypted = PacketHandler.encrypt(url.getBytes());
                out.writeInt(encrypted.length);
                out.write(encrypted);
                out.flush();

                // Read response
                int length = in.readInt();
                byte[] encryptedResp = new byte[length];
                in.readFully(encryptedResp);

                String response = new String(PacketHandler.decrypt(encryptedResp));
                System.out.println("Response:\n" + response);
            }

        } catch (IOException e) {
            logger.severe("Client error: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Encryption error: " + e.getMessage());
        }
    }
}
