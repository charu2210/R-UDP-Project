package com.rudp.core;

/**
 * NetworkConfig: The central tuning station for our R-UDP protocol.
 * In a real-world Cisco environment, these might be loaded from a .conf file.
 */
public class NetworkConfig {
    
    // The door your data walks through
    public static final int DEFAULT_PORT = 9876;
    
    // Standard Ethernet MTU is 1500 bytes. 
    // We use 1024 for the payload to stay safely under that limit after adding headers.
    public static final int PAYLOAD_SIZE = 1024; 
    
    // How many 'Lego bricks' can be in the air before we stop and wait for an ACK?
    // A larger window is faster but requires more memory (RAM).
    public static final int SLIDING_WINDOW_SIZE = 5;
    
    // If we don't hear back from the receiver in 500ms, we assume the packet is lost.
    public static final int RETRANSMISSION_TIMEOUT_MS = 500;
    
    // Maximum number of times we try to re-send a single packet before giving up.
    public static final int MAX_RETRIES = 3;
    
    // Buffer size for the UDP socket - must be larger than HEADER_SIZE + PAYLOAD_SIZE.
    public static final int RECEIVE_BUFFER_SIZE = 2048;

    // Protocol Flags (Think of these as the 'Status Lights' on a router)
    public static final byte FLAG_DATA = 0x01;
    public static final byte FLAG_ACK  = 0x02;
    public static final byte FLAG_SYN  = 0x03; // For the handshake
    public static final byte FLAG_FIN  = 0x04; // For closing the connection
}