package com.javavpn.config;

import java.io.InputStream;
import java.util.Properties;

public class VPNConfig {
    public String serverIp;
    public int port;
    public String encryptionKey;

    public VPNConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("vpn-config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            this.serverIp = prop.getProperty("server.ip");
            this.port = Integer.parseInt(prop.getProperty("server.port"));
            this.encryptionKey = prop.getProperty("encryption.key");

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }
}
