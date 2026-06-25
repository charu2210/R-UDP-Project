# Reliable UDP (R-UDP)

A Java implementation of a lightweight Reliable UDP (R-UDP) transport protocol built to explore how reliability can be implemented on top of UDP. The project demonstrates packet framing, checksum validation, concurrent packet tracking, acknowledgement handling, and sliding-window based flow control concepts.

---

# Motivation

UDP provides fast communication but offers no guarantees for:

- packet ordering
- packet delivery
- duplicate protection
- corruption detection
- retransmission

This project recreates several of the reliability mechanisms commonly found in TCP while keeping the implementation modular and easy to understand.

---

# Features

## Custom Packet Format

Every packet contains a custom 12-byte application-layer header.

| Field | Size |
|--------|------|
| Sequence Number | 4 bytes |
| CRC32 Checksum | 4 bytes |
| Payload Length | 4 bytes |

The payload immediately follows the header.

```
+-------------------------------+
| Sequence Number (4 bytes)     |
+-------------------------------+
| CRC32 Checksum (4 bytes)      |
+-------------------------------+
| Payload Length (4 bytes)      |
+-------------------------------+
| Payload                       |
+-------------------------------+
```

---

## Packet Serialization

Packets are serialized using Java's `ByteBuffer`.

Each packet can be converted into raw bytes before transmission and reconstructed back into a packet object on the receiver side.

Implemented in:

```
Packet.java
```

---

## CRC32 Integrity Verification

Every outgoing packet computes a CRC32 checksum.

The receiver recomputes the checksum and validates packet integrity before accepting the payload.

Corrupted packets are rejected.

---

## Sequence Numbers

Each packet is assigned an increasing sequence number.

Sequence numbers allow the receiver to:

- identify packets
- reconstruct messages
- prepare for reliable delivery logic

---

## Sliding Window Flow Control

The sender limits the number of in-flight packets using a configurable sliding window.

The implementation uses:

```
ConcurrentHashMap<Integer, Packet>
```

to safely track outstanding packets during concurrent execution.

Window size is configurable through:

```
NetworkConfig.java
```

---

## Concurrent Packet Tracking

Outstanding packets are stored in a thread-safe data structure.

```java
ConcurrentHashMap<Integer, Packet>
```

This prepares the sender for asynchronous acknowledgement processing and retransmission extensions.

---

## Receiver-side Packet Ordering

The receiver stores received payloads inside

```java
TreeMap<Integer, byte[]>
```

allowing packets to be reconstructed according to sequence number.

---

## ACK Generation

After validating a packet,

the receiver sends a 4-byte acknowledgement containing the received sequence number.

This acknowledgement mechanism forms the basis for reliable delivery.

---

## Configurable Network Parameters

The protocol centralizes all networking parameters inside

```
NetworkConfig.java
```

including:

- Port
- Payload size
- Window size
- Timeout
- Retry count
- Receive buffer

allowing protocol tuning without changing business logic.

---

# Architecture

```
Application
      │
      ▼
Packet Builder
      │
      ▼
Custom Header
      │
      ▼
UDP Socket
      │
──────────── Network ────────────
      │
      ▼
UDP Socket
      │
      ▼
Packet Parser
      │
      ▼
CRC Verification
      │
      ▼
TreeMap Reassembly
      │
      ▼
ACK Generation
```

---

# Project Structure

```
src/
└── com
    └── rudp
        └── core
            ├── Packet.java
            ├── Sender.java
            ├── Receiver.java
            ├── NetworkConfig.java
            └── ChecksumVerifier.java
```

---

# Technologies

- Java
- UDP Socket API
- ByteBuffer
- CRC32
- ConcurrentHashMap
- TreeMap
- Multithreading Concepts
- Transport Layer Networking

---

# Networking Concepts Demonstrated

- Reliable Data Transfer
- UDP Communication
- Packet Framing
- Custom Protocol Design
- Sequence Numbers
- CRC-based Error Detection
- Sliding Window Concepts
- Concurrent Packet Tracking
- Socket Programming

---

# Learning Outcomes

This project was built to gain a deeper understanding of transport-layer networking by implementing several TCP-inspired reliability mechanisms directly over UDP. It explores packet design, checksum validation, acknowledgement handling, concurrent data structures, and protocol engineering using Java.
