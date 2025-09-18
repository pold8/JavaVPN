package com.javavpn.network;

import com.javavpn.crypto.EncryptionHandler;

public class PacketHandler {
    private static final String KEY = "1234567890123456"; // must be 16 bytes for AES

    public static byte[] encrypt(byte[] data) throws Exception {
        return EncryptionHandler.encrypt(data, KEY);
    }

    public static byte[] decrypt(byte[] data) throws Exception {
        return EncryptionHandler.decrypt(data, KEY);
    }
}
