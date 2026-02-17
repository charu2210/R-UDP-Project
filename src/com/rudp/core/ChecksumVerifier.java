package com.rudp.core;

import java.nio.file.*;
import java.security.MessageDigest;
import java.math.BigInteger;

public class ChecksumVerifier {
    public static String getFileHash(String filePath) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(filePath));
        byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        return new BigInteger(1, hash).toString(16);
    }

    public static void main(String[] args) throws Exception {
        String original = getFileHash("data/input.zip");
        String received = getFileHash("data/received.zip");

        if (original.equals(received)) {
            System.out.println("SUCCESS: Files are identical. R-UDP integrity confirmed.");
        } else {
            System.out.println("FAILURE: File mismatch. Check your retransmission logic.");
        }
    }
}