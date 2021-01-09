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
import java.util.Queue;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
    private Text textoAdvertencia = new Text();
    private BT<String> arbol = new BT<>();
    
    @Override
    public void start(Stage primaryStage) {
        
        root = new Pane();
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
        textoAdvertencia.setLayoutX(180);
        textoAdvertencia.setLayoutY(440);
        root.getChildren().add(enviar);
        root.getChildren().add(codigo);
        root.getChildren().add(textoAdvertencia);
        HashMap<String,List<String>> mapaMorse = arbol.leerTraducciones();
        for(Map.Entry<String,List<String>> dato : mapaMorse.entrySet()){
            arbol.añadirMorse(dato.getKey(),dato.getValue());
        }
        Circle cir = arbol.movCirculo();
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
        boton.setOnAction((new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               if(verificarCode(codigo.getText())){
                String morse = codigo.getText().replace(" ","");
                textoAdvertencia.setText("");
                Sonido audio = new Sonido();
                audio.setCode(morse);
                audio.start();
                audio = null;
            }
            else{
                textoAdvertencia.setText("El código es Incorrecto, agregue solo . y -");
            }
            }    
        }));
    }
    public boolean verificarCode(String texto){
        for (int i = 0; i < texto.length(); i++) {
             char c = texto.charAt(i);
             if(c!= '.' && c!= '-'){
                 return false;
             }
        }
        return true;
    }
    
    public Queue<String> listaCodes(String valor){
        Queue<String> cola = new LinkedList<>();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            cola.offer(c+"");     
        }
        
        return cola;
    }
}
