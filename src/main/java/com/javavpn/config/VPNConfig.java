package com.javavpn.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VPNConfig {
    private String host;
    private int port;

    public VPNConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("vpn.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                this.host = prop.getProperty("vpn.server.host", "127.0.0.1");
                this.port = Integer.parseInt(prop.getProperty("vpn.server.port", "8080"));
            } else {
                System.err.println("Could not load vpn.properties file. Using defaults.");
                this.host = "127.0.0.1";
                this.port = 8080;
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.host = "127.0.0.1";
            this.port = 8080;
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
