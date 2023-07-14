# ClientUDP and ServerUDP

This repository contains Java code for a simple client-server communication using UDP (User Datagram Protocol). The code consists of two classes: `ClientUDP` and `ServerUDP`, which implement the client and server sides of the communication, respectively.

## ClientUDP

The `ClientUDP` class represents the client side of the UDP communication. It creates a UDP socket and sends a datagram packet to the server. The user is prompted to enter the number of entries and the name of a person, which are then sent to the server. The server's address and port are provided as input. After sending the datagram packet, the client waits for a reply from the server. Once the reply is received, it is displayed on the console, and the client socket is closed.

## ServerUDP

The `ServerUDP` class represents the server side of the UDP communication. It creates a UDP socket and listens for incoming datagram packets from clients. Each received packet contains the number of entries and the name of a person. The server processes the received data, performs a ticket booking operation (simulated by the `booking` method), and sends a reply back to the client. The server keeps track of the number of requests from each client using a `Map<String, Integer>`. The server runs indefinitely, continuously listening for incoming packets.

## How to Run

To run the code, follow these steps:

1. Compile the Java files: `javac ClientUDP.java ServerUDP.java`
2. Start the server: `java ServerUDP`
3. Start the client: `java ClientUDP`
4. Follow the prompts in the client console to enter the number of entries and the name of a person.

Please note that the server should be started before running the client.

## Requirements

- Java Development Kit (JDK)
- Java compatible operating system

