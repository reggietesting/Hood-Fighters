import java.util.*;
import javax.swing.text.Position;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;



public class NetworkServer<T> extends Thread {
    public static ServerSocket UNIVERSAL_SOCKET;

    public static ArrayList<PositionReplication> REPLICATING_CLIENTS;
    public static ArrayList<InputManager> INPUT_MANAGERS;

    public static void Connect(int port, String type) throws IOException {
        Thread connectionThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket newPlayer = serverSocket.accept();

                    if (type.equals("PositionReplication")) {
                       PositionReplication playerClient = new PositionReplication(newPlayer);
                       REPLICATING_CLIENTS.add(playerClient);
                       playerClient.start();
                    } 

                    if (type.equals("InputManager")) {
                        InputManager playerClient = new InputManager(newPlayer);
                       INPUT_MANAGERS.add(playerClient);
                       playerClient.start();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        connectionThread.start();
    }

    public static void main(String[] args) {
        try {
            // UNIVERSAL_SOCKET = new ServerSocket(8080);
            REPLICATING_CLIENTS = new ArrayList<PositionReplication>();
            INPUT_MANAGERS = new ArrayList<InputManager>();

            Connect(8080, "PositionReplication");
            Connect(9090, "InputManager");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

// replicates client pivot to every other client
// amen amen amen amen amen amen amen amen amen


class PositionReplication extends Thread {
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    Socket client;
    String ID;
    ReplicatorCompiler compiler;

    public PositionReplication(Socket client) throws IOException {
        inputStream = new ObjectInputStream(client.getInputStream());
        outputStream = new ObjectOutputStream(client.getOutputStream());
        this.client = client;

        ID = UUID.randomUUID().toString();
        compiler = new ReplicatorCompiler(ID);
    }

    public ObjectOutputStream getOutputStream() {
        return this.outputStream;
    }


    public synchronized void run() {
        while (true) {
            // i don't know what we are going to encode pivots in
            try {
                String result = (String) inputStream.readObject();
                compiler.SetPivot(result);
                
                for (int i = 0; i < NetworkServer.REPLICATING_CLIENTS.size(); i++) {
                    PositionReplication otherClient = NetworkServer.REPLICATING_CLIENTS.get(i);
                    
                    if (otherClient.equals(this)) {
                        continue;
                    }

                    otherClient.getOutputStream().writeObject(compiler);
                }

            } catch (Exception e) {
                // TODO: handle exception
            }


        }
    }
}

class InputManager extends Thread {
    public InputManager(Socket client) {

    }

    public synchronized void run() {
        
    }
}