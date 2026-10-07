package api.connector;

import java.io.*;
import java.net.*;

public class connector {
    private final int port = 5555;
    private DatagramSocket socket;
    private InetAddress targetIP;

    public void initiate(String targetIP) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(port);
        this.targetIP = InetAddress.getByName(targetIP);
    }

    public void send(String message) throws IOException {
        byte[] data = message.getBytes();
        int size = data.length;
        DatagramPacket packet = new DatagramPacket(data, size, this.targetIP, this.port);
        socket.send(packet);
    }

    public String receive() throws IOException {
        byte[] data = new byte[1518];
        int size = data.length;
        DatagramPacket packet = new DatagramPacket(data, size);
        socket.receive(packet);
        return new String(packet.getData(),0,packet.getLength());
    }

    public boolean connectionTest() {
        String testMessage = "test";
        String testAnswer = null;
        try {
            send(testMessage);
            testAnswer = receive();
            if (testAnswer.contains("test")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public void terminate() {
        socket.close();
    }
}