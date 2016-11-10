package org.academiadecodigo.udpserver;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Created by codecadet on 07/11/16.
 */
public class UDPClient {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String message;
        String receiveMessage;
        String hostName = "localhost";
        int portNumber = 5000;

        byte[] sendBuffer;
        byte[] recvBuffer;

        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();

            while (true) {

                DatagramPacket sendPacket = null;
                System.out.println("Write your message");
                message = input.nextLine();

                if (message.toLowerCase().compareTo("exit") == 0)
                    break;

                sendBuffer = message.getBytes();

                sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(hostName), portNumber);


                socket.send(sendPacket);

                System.out.println("message sent");

                recvBuffer = new byte[message.length()];
                DatagramPacket receivePacket = new DatagramPacket(recvBuffer, recvBuffer.length);
                socket.receive(receivePacket); // blocks while packet not received
                receiveMessage = new String(recvBuffer, 0, receivePacket.getLength());

                if (receiveMessage.compareTo("down") == 0)
                    break;
                System.out.println("Server Message: \n" + receiveMessage);

            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            System.out.println("Closing connection. Bye bye");
            socket.close();
        }


    }
}
