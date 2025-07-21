package com.javavpn.server;

import com.javavpn.config.VPNConfig;
import com.javavpn.crypto.EncryptionHandler;
import com.javavpn.network.TunnelManager;
import com.javavpn.utils.LoggerUtil;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class VPNServer {
    private final VPNConfig config;
    private static final Logger logger = LoggerUtil.createLogger(VPNServer.class);

    public VPNServer(VPNConfig config) {
        this.config = config;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(config.port)) {
            logger.info("VPN server started on port " + config.port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: " + clientSocket.getInetAddress());

                // Thread per client
                new Thread(() -> handleClient(clientSocket)).start();
            }

        } catch (Exception e) {
            logger.severe("Server error: " + e.getMessage());
        }
    }

    private void handleClient(Socket socket) {
        try {
            InputStream in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int length = in.read(buffer);

            byte[] received = new byte[length];
            System.arraycopy(buffer, 0, received, 0, length);

            byte[] decrypted = EncryptionHandler.decrypt(received, config.encryptionKey);
            logger.info("Decrypted message: " + new String(decrypted));

            // Start tunnel thread
            new TunnelManager().startTunnel(socket);

        } catch (Exception e) {
            logger.warning("Error handling client: " + e.getMessage());
        }
    }
}
