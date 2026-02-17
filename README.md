# Reliable-UDP (R-UDP) Transport Protocol

## Overview
A custom transport-layer implementation designed to provide reliability over the inherently unreliable UDP protocol. This project demonstrates deep-dive knowledge of the **TCP/IP stack** and **Socket Programming**.

## Key Technical Features
* **Selective Repeat ARQ:** Implemented per-packet retransmission timers to ensure high efficiency.
* **Sliding Window Flow Control:** Engineered a dynamic window mechanism to handle ordered delivery and prevent receiver buffer overflow.
* **Integrity Validation:** Integrated **CRC32-based checksums** to detect and discard corrupted packets.
* **Network Emulation:** Includes a proxy to simulate **20% packet loss**, duplication, and latency.

## Performance Metrics
* **Goodput:** Maintained **90% goodput** under a simulated 20% packet loss environment.
* **Concurrency:** Utilized **thread-safe buffers** to handle asynchronous ACK tracking.

## Tech Stack
* **Language:** Java 
* **Concepts:** Multithreading, Concurrent Systems, Socket API 
