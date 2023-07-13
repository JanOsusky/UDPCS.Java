import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServerUDP {

    public static String booking(String s){
        String res = "";
        String [] ERRORS = {"Sold out", "System Error", "Try again later", "Request Error"};
        double random = Math.random();
        if (random < 0.8)
            res = "Successfull booking";
        else res = "Failed booking: " + ERRORS[new Random().nextInt(ERRORS.length)];

        return res;
    }

    public static void main(String[] args)
    {
        Integer port = null;
        // SOCKET
        DatagramSocket server = null;

        // Tickets per client
        Map<String, Integer> tickets = new HashMap<String, Integer>();

        // Create and initialise server's socket
        try {
            server = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Could not listen on port "+port);
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        // Server MAIN function
        while (true)
        {
            System.out.println("Waiting for a new UDP client");

            // Creates and initializes an EMPTY datagram to receive the response of maximum 200 bytes
            byte [] buffer = new byte[200];
            DatagramPacket receivedDatagram = new DatagramPacket(
                    buffer,             // Memory area where the readings are stored
                    buffer.length       // Size of this area
            );

		    // Received datagram
            try {
                server.receive(receivedDatagram);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Gets the received text
            String line = new String(receivedDatagram.getData(),
                    receivedDatagram.getOffset(),
                    receivedDatagram.getLength(),
                    StandardCharsets.UTF_8
            );

            // Displays the socket address (IP and port) of the client and its text
            System.out.println("Message from client " + receivedDatagram.getAddress() + ":" + receivedDatagram.getPort() + " > " + line);

            String client = receivedDatagram.getAddress() + ":" + receivedDatagram.getPort();
            Integer requests = tickets.get(client);
            Integer new_requests = Character.getNumericValue(line.charAt(0));
            if(requests == null){
                requests = new_requests;
            } else {
                requests += new_requests;
            }
            tickets.put(client, requests);

            // Ticket booking
            // If the format is incorrect, the function returns "ERROR"
            line = booking(line);

            System.out.println("STATUS: Reply sent -> " + line);
        } // End of service loop
    } 
    
}
