package com.javavpn.network;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TunnelManager {
    public void startTunnel(Socket socket) {
        new Thread(() -> {
            try {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                byte[] buffer = new byte[1024];
                int length;

                while ((length = in.read(buffer)) != -1) {
                    byte[] packet = new byte[length];
                    System.arraycopy(buffer, 0, packet, 0, length);

                    // Encrypt before sending (or decrypt depending on direction)
                    byte[] modified = PacketHandler.encrypt(packet);

                    out.write(modified);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
