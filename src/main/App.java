/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import system.BT;
import system.Sonido;

/**
 *
 * @author El Pitagoras
 */
public class App extends Application {
    static Pane root;
    private Button enviar;
    private TextField codigo;
    
    @Override
    public void start(Stage primaryStage) {
        
        root = new Pane();
        BT<String> arbol = new BT<>();
        Scene scene = new Scene(root, 600, 450);
        primaryStage.setTitle("TreeMorse");
        primaryStage.setScene(scene);
        primaryStage.show();
        enviar = new Button("Enviar");
        codigo = new TextField();
        enviar.setLayoutX(380);
        enviar.setLayoutY(400);
        codigo.setLayoutX(180);
        codigo.setLayoutY(400);
        root.getChildren().add(enviar);
        root.getChildren().add(codigo);
        HashMap<String,List<String>> mapaMorse = arbol.leerTraducciones();
        for(Map.Entry<String,List<String>> dato : mapaMorse.entrySet()){
            arbol.añadirMorse(dato.getKey(),dato.getValue());
        }   
        
        accionBoton(enviar);
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
    
    public void accionBoton(Button boton){
        boton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Me han aplastado");
            }
        });
    }
}
