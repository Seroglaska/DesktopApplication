package by.max.coourseproject.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;


public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientAccepted;


    public static final int PORT = 2525;
    public static LinkedList<Client> serverList = new LinkedList<>(); // список всех нитей


    public void start()  {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    serverList.add(new Client(socket)); // добавить новое соединенние в список
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
