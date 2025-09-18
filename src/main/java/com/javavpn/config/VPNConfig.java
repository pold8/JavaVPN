package com.javavpn.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class VPNConfig {
    private String host;
    private int port;
    private final Map<String, String> countryHosts = new HashMap<>();

    // ✅ Default constructor: load from vpn.properties
    public VPNConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("vpn.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);

                // Default server (fallback)
                this.host = prop.getProperty("vpn.server.host", "127.0.0.1");
                this.port = Integer.parseInt(prop.getProperty("vpn.server.port", "8080"));

                // Load country-specific mappings
                for (String key : prop.stringPropertyNames()) {
                    if (key.startsWith("vpn.server.country.")) {
                        String country = key.replace("vpn.server.country.", "").toUpperCase();
                        countryHosts.put(country, prop.getProperty(key));
                    }
                }

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

    // ✅ New constructor: manual override
    public VPNConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getHostForCountry(String countryCode) {
        return countryHosts.getOrDefault(countryCode.toUpperCase(), host);
    }

    public Map<String, String> getAvailableCountries() {
        return countryHosts;
    }
}
