package com.javavpn.server;

import com.javavpn.config.VPNConfig;
import com.javavpn.utils.LoggerUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class VPNServer {
    private static final Logger logger = LoggerUtil.createLogger(VPNServer.class);
    private final VPNConfig config;
    private final ExecutorService clientThreadPool = Executors.newCachedThreadPool();

    public VPNServer(VPNConfig config) {
        this.config = config;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(config.getPort())) {
            logger.info("VPN server started on port " + config.getPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New client connected from " + clientSocket.getInetAddress());
                clientThreadPool.submit(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {
            logger.severe("Server error: " + e.getMessage());
        }
    }
}
