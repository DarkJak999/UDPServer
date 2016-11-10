package org.academiadecodigo.udpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.InetAddress;

/**
 * Created by codecadet on 07/11/16.
 */
public class UDPServer {

    public static void main(String[] args) {

        int portNumber = 5000;

        byte[] sendBuffer = new byte[1024];
        byte[] recvBuffer = new byte[1024];
        byte[] messageByte = new byte[1024];

        InetAddress replyAddress;
        int replyPort;

        String message = "";

        DatagramSocket socket = null;
        try {
            System.out.println("Opening server");
            socket = new DatagramSocket(portNumber);

            while (true) {

                System.out.println("Waiting for packet");

                DatagramPacket receivePacket = new DatagramPacket(recvBuffer, recvBuffer.length);

                socket.receive(receivePacket);

                message = new String(recvBuffer, 0, receivePacket.getLength());
                System.out.println("Uppercased message: " + message.toUpperCase());

                if (message.compareTo("terminate") == 0) {
                    replyAddress = receivePacket.getAddress();
                    replyPort = receivePacket.getPort();
                    sendBuffer = "down".getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, replyAddress, replyPort);
                    socket.send(sendPacket);
                    break;
                }

                sendBuffer = message.toUpperCase().getBytes();

                System.out.println("sending message");
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());


                socket.send(sendPacket);
                System.out.println("message sent");
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Closing server");
            socket.close();
        }


    }

}
