package by.max.coourseproject.server.main;

import by.max.coourseproject.server.controller.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends Thread {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    Client(Socket socket) throws IOException{
        this.socket = socket;
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    @Override
    public void run() {
        try {
            Controller controller = new Controller();
            controller.control(ois, oos, socket);
        }catch (SocketException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
             if (socket != null)
                    socket.close();
            } catch (IOException e) {
            e.printStackTrace();
            }
            try {
                if (oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    }

    public Socket getSocket() {
        return socket;
    }
}
