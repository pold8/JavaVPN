package com.javavpn;

import com.javavpn.client.VPNClient;
import com.javavpn.server.VPNServer;
import com.javavpn.config.VPNConfig;

public class Main {
    public static void main(String[] args) {
        VPNConfig config = new VPNConfig();

        if(args.length > 0 && args[0].equalsIgnoreCase("server")) {
            VPNServer server = new VPNServer(config);
            server.start();
        }else {
            VPNClient client = new VPNClient(config);
            client.connect();
        }
    }
}