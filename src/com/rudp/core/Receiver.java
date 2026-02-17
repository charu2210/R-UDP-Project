package com.rudp.core;

import java.net.*;
import java.util.*;
import java.nio.ByteBuffer;

public class Receiver {
    private DatagramSocket socket;
    private TreeMap<Integer, byte[]> receivedData = new TreeMap<>();

    public void receive(int port) throws Exception {
        socket = new DatagramSocket(port);
        byte[] buffer = new byte[NetworkConfig.RECEIVE_BUFFER_SIZE];

        while (true) {
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            socket.receive(dp);

            // Extract the packet using our custom header logic
            Packet p = Packet.fromBytes(dp.getData());
            
            // Verification logic: CRC check
            long calculatedCrc = Packet.calculateChecksum(p.getData());
            if (calculatedCrc == p.getChecksum()) {
                System.out.println("Receiver: Validated Packet " + p.getSeqNum());
                receivedData.put(p.getSeqNum(), p.getData());
                
                // Print the current reconstructed message
                reconstructMessage();
                
                // Send back an ACK (Required for Reliability)
                sendAck(p.getSeqNum(), dp.getAddress(), dp.getPort());
            } else {
                System.out.println("Receiver: Corruption detected in Packet " + p.getSeqNum());
            }
        }
    }

    private void reconstructMessage() {
        StringBuilder sb = new StringBuilder();
        for (byte[] chunk : receivedData.values()) {
            sb.append(new String(chunk));
        }
        System.out.println("Current Buffer: " + sb.toString());
    }

    private void sendAck(int seqNum, InetAddress addr, int port) throws Exception {
        byte[] ack = ByteBuffer.allocate(4).putInt(seqNum).array();
        socket.send(new DatagramPacket(ack, ack.length, addr, port));
    }

    public static void main(String[] args) {
        try {
            Receiver server = new Receiver();
            System.out.println("Receiver: Listening for incoming R-UDP traffic on port " + NetworkConfig.DEFAULT_PORT + "...");
            server.receive(NetworkConfig.DEFAULT_PORT);
        } catch (Exception e) {
            System.err.println("Receiver Error: " + e.getMessage());
        }
    }
}