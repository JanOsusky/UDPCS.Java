import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ClientUDP {
    public static void main(String[] args) throws IOException {
        String serverName = null;
        int serverPort = -1;

        DatagramSocket serviceSocket = null;

        // Create socket
        try
        {
            serviceSocket = new DatagramSocket();
        }
        catch (SocketException e)
        {
            System.err.println("ERROR: Could not create the socket");
            System.exit(1);
        }

        // INITIALIZES KEYBOARD INPUT
        BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in) );
        String userInput;
        System.out.println("Indicate the number of entries (0 to finish): ");
        userInput = stdIn.readLine(); /*STRING STORED IN userInput*/

        System.out.println("Enter the name of the person: ");
        userInput += stdIn.readLine(); /*STRING STORED IN userInput*/
        // Create datagram with string written in body
        DatagramPacket datagram = null;
        try {
            datagram = new DatagramPacket(
                            userInput.getBytes(StandardCharsets.UTF_8),               // Data sent
                            userInput.getBytes(StandardCharsets.UTF_8).length,        // Data size
                            InetAddress.getByName(serverName),  // Server address
                            serverPort                          // Server port
            );
        } catch (UnknownHostException e) {
            System.err.println("ERROR: Could not resolve server address");
            System.exit(1);
        }

        // Sends datagram through socket
        try {
            serviceSocket.send(datagram);
        } catch (IOException iOException) {
            System.err.println("ERROR: Could not send datagram");
            System.exit(1);
        }

        System.out.println("STATUS: Waiting for the reply");

        // Creates and initializes an EMPTY datagram to receive the response of maximum 200 bytes (2x100).
        byte [] buffer = new byte[200];
        DatagramPacket receivedDatagram = new DatagramPacket(
                buffer,             // Memory area where the readings are stored
                buffer.length       // Size of this area
        );

        // Received the replied datagram
        try {
            do{
                serviceSocket.receive(receivedDatagram);
            }while(false); // Change the condition for solving section 1F
        } catch (IOException e) {
            System.err.println("ERROR: No datagram could be received");
            System.exit(1);
        }

        // Extracts the content of the datagram body in variable line
        String line = new String(receivedDatagram.getData(),
                            receivedDatagram.getOffset(),
                            receivedDatagram.getLength(),
                            StandardCharsets.UTF_8
        );

        System.out.println("echo: " + line);

        System.out.println("STATUS: Closing client");

        // Closing Client's socket
        serviceSocket.close();

        System.out.println("STATUS: closed");
    }
}
