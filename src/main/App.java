/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        BT<String> arbol = new BT<>();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        HashMap<String,List<String>> mapaMorse = arbol.leerTraducciones();
        for(Map.Entry<String,List<String>> dato : mapaMorse.entrySet()){
            arbol.añadirMorse(dato.getKey(),dato.getValue());
        }
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
