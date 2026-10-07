package api.connector;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class connectortest {
    public static void main(String[] args) {
        connector c = new connector();
        String IP = "172.17.200.213";
        try {
            c.initiate(IP);
            System.out.println("Initialisiert");
            System.out.println("erfolgreich: " + c.connectionTest());
            c.terminate();
        } catch (SocketException e) {
            System.out.println("Socket fehler");
        } catch (UnknownHostException e) {
            System.out.println("IP fehlerhaft");
        }
    }
}