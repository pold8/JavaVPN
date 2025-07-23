package com.javavpn;

import com.javavpn.client.VPNClient;
import com.javavpn.server.VPNServer;
import com.javavpn.config.VPNConfig;
import com.javavpn.utils.LoggerUtil;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = LoggerUtil.createLogger(Main.class);

    public static void main(String[] args) {
        VPNConfig config = new VPNConfig();

        if (args.length > 0 && args[0].equalsIgnoreCase("server")) {
            logger.info("Starting VPN Server...");
            new VPNServer(config).start();
        } else {
            logger.info("Starting VPN Client...");
            new VPNClient(config).connect();
        }
    }
}
