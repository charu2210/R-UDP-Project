package com.rudp.core;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public class Packet {
    public static final int HEADER_SIZE = 12; 
    private int seqNum;
    private long checksum; // This is what we need to access
    private byte[] data;

    public Packet(int seqNum, byte[] data) {
        this.seqNum = seqNum;
        this.data = data;
        this.checksum = calculateChecksum(data);
    }

    public static long calculateChecksum(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }

    // THIS IS THE MISSING PIECE THE COMPILER IS SCREAMING ABOUT
    public long getChecksum() {
        return checksum;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public byte[] getData() {
        return data;
    }

    public byte[] toBytes() {
        ByteBuffer buf = ByteBuffer.allocate(HEADER_SIZE + data.length);
        buf.putInt(seqNum);
        buf.putInt((int) checksum); // Store as int for the header
        buf.putInt(data.length);
        buf.put(data);
        return buf.array();
    }

    public static Packet fromBytes(byte[] bytes) {
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        int seq = buf.getInt();
        long crc = buf.getInt() & 0xFFFFFFFFL; // Convert back to unsigned long
        int len = buf.getInt();
        byte[] payload = new byte[len];
        buf.get(payload);
        
        Packet p = new Packet(seq, payload);
        p.checksum = crc; // Manually set the checksum from the header
        return p;
    }
}