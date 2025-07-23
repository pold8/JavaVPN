package com.javavpn.client;

import com.javavpn.config.VPNConfig;
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
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            logger.info("Connected to VPN server.");

            // Read welcome message
            System.out.println(in.readLine());

            // Send messages from console
            String input;
            while (true) {
                System.out.print("You > ");
                input = scanner.nextLine();
                out.write(input + "\n");
                out.flush();

                String response = in.readLine();
                System.out.println("Server > " + response);
            }

        } catch (IOException e) {
            logger.severe("Client error: " + e.getMessage());
        }
    }
}
