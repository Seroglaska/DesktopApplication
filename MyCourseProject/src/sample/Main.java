package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {
    private static Socket clientSocket;
    private static Scanner sc;
    private static ObjectOutputStream coos;
    private static ObjectInputStream cois;

    public static ObjectOutputStream getCoos() {
        return coos;
    }

    public static ObjectInputStream getCois() {
        return cois;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("files/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        System.out.println("server connecting....");
        clientSocket = new Socket("127.0.0.1",2525);
        System.out.println("connection established....");
        coos = new ObjectOutputStream(clientSocket.getOutputStream());
        cois = new ObjectInputStream(clientSocket.getInputStream());
        launch(args);
    }
}
