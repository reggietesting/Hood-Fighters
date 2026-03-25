import java.util.*;

import javax.swing.text.Position;

import java.io.IOException;
import java.net.*;

public class NetworkServer {
    public static ServerSocket UNIVERSAL_SOCKET;
    public static ArrayList<PositionReplication> REPLICATING_CLIENTS;

    public static void Connect() throws IOException {
        while (true) {
            Socket newPlayer = UNIVERSAL_SOCKET.accept();
            PositionReplication playerClient = new PositionReplication();
            REPLICATING_CLIENTS.add(playerClient);
        }
    }

    public static void main(String[] args) {
        try {
            UNIVERSAL_SOCKET = new ServerSocket(8080);
            REPLICATING_CLIENTS = new ArrayList<PositionReplication>();
            Connect();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

class PositionReplication extends Thread {
    public PositionReplication(Socket ) {

    }
}