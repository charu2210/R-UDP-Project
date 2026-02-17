package com.rudp.core;

import java.net.*;
import java.util.concurrent.*;
import java.nio.ByteBuffer;

public class Sender {
    private DatagramSocket socket;
    private int windowSize = NetworkConfig.SLIDING_WINDOW_SIZE;
    private ConcurrentHashMap<Integer, Packet> unackedPackets = new ConcurrentHashMap<>();

    public void sendFile(byte[] fileData, InetAddress address, int port) throws Exception {
        socket = new DatagramSocket();
        // Set a timeout for the socket so it doesn't wait for ACKs forever
        socket.setSoTimeout(NetworkConfig.RETRANSMISSION_TIMEOUT_MS);
        
        int totalPackets = (int) Math.ceil((double) fileData.length / NetworkConfig.PAYLOAD_SIZE);

        for (int i = 0; i < totalPackets; i++) {
            // Flow Control: Don't overflow the window
            while (unackedPackets.size() >= windowSize) { 
                Thread.sleep(10); 
            }

            byte[] chunk = extractChunk(fileData, i);
            Packet p = new Packet(i, chunk);
            unackedPackets.put(i, p);
            
            byte[] sendData = p.toBytes();
            socket.send(new DatagramPacket(sendData, sendData.length, address, port));
            System.out.println("Sender: Sent Packet " + i);
            
            // In a real Cisco environment, we'd listen for ACKs in a separate thread.
            // For this version, we assume the Receiver is fast.
        }
        
        // Clean up
        socket.close();
    }

    private byte[] extractChunk(byte[] data, int index) {
        int start = index * NetworkConfig.PAYLOAD_SIZE;
        int length = Math.min(data.length - start, NetworkConfig.PAYLOAD_SIZE);
        byte[] chunk = new byte[length];
        System.arraycopy(data, start, chunk, 0, length);
        return chunk;
    }

    public static void main(String[] args) {
        try {
            Sender client = new Sender();
            String message = "Hello Cisco! This is a reliable message sent via my custom R-UDP protocol. Testing 1... 2... 3...";
            byte[] data = message.getBytes();
            
            System.out.println("Sender: Initializing transfer to localhost...");
            client.sendFile(data, InetAddress.getByName("localhost"), NetworkConfig.DEFAULT_PORT);
            System.out.println("Sender: Data stream finished.");
        } catch (Exception e) {
            System.err.println("Sender Error: " + e.getMessage());
        }
    }
}