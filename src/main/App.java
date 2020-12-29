/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.LinkedList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import system.BT;

/**
 *
 * @author El Pitagoras
 */
public class App extends Application {
    static Pane root;
    
    @Override
    public void start(Stage primaryStage) {
        
        root = new Pane();
        BT<Integer> arbol = new BT<>();
        Scene scene = new Scene(root, 800, 600);
        LinkedList<String> ll = new LinkedList<>();
        ll.add("-");
        ll.add(".");
        ll.add(".");
        ll.add("-");
        ll.add(".");
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        arbol.añadirMorse("A", ll);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void agregarNodo(Node n) {
        root.getChildren().add(n);
    }
}
