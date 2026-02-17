# Reliable-UDP (R-UDP) Transport Protocol

## Overview
A custom transport-layer implementation designed to provide reliability over the inherently unreliable UDP protocol. This project demonstrates deep-dive knowledge of the **TCP/IP stack** and **Socket Programming**.

## Key Technical Features
* [cite_start]**Selective Repeat ARQ:** Implemented per-packet retransmission timers to ensure high efficiency.
* [cite_start]**Sliding Window Flow Control:** Engineered a dynamic window mechanism to handle ordered delivery and prevent receiver buffer overflow[cite: 21].
* [cite_start]**Integrity Validation:** Integrated **CRC32-based checksums** to detect and discard corrupted packets[cite: 23].
* [cite_start]**Network Emulation:** Includes a proxy to simulate **20% packet loss**, duplication, and latency[cite: 24].

## Performance Metrics
* [cite_start]**Goodput:** Maintained **90% goodput** under a simulated 20% packet loss environment.
* [cite_start]**Concurrency:** Utilized **thread-safe buffers** to handle asynchronous ACK tracking[cite: 23].

## Tech Stack
* [cite_start]**Language:** Java [cite: 19]
* [cite_start]**Concepts:** Multithreading, Concurrent Systems, Socket API [cite: 19]
