/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import system.BT;
import system.Sonido;

/**
 *
 * @author El Pitagoras
 */
public class App extends Application {

    public static Pane root;
    private Button enviar = new Button("Enviar");
    private TextField codigo = new TextField();
    private static Text textoMuestra = new Text();
    public BT<String> arbol = new BT<>();
    private Text nombrePagina = new Text("Árbol de Código Morse");

    @Override
    public void start(Stage primaryStage) {

        root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("TreeMorse");
        primaryStage.setScene(scene);
        primaryStage.show();
        posicion();
        addNodes();
        arbol.llenarArbol();
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

    public void accionBoton(Button boton) {
        boton.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    String morse = codigo.getText().replace(" ", "").toUpperCase();
                    arbol.reiniciarColor();
                    arbol.pintar(morse);                
            }
        }));
    }

    public boolean verificarCode(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c != '.' && c != '-') {
                return false;
            }
        }
        return true;
    }
    
    public static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent) {
                addAllDescendents((Parent) node, nodes);
            }
        }
    }

    public void addNodes() {
        root.getChildren().add(enviar);
        root.getChildren().add(codigo);
        root.getChildren().add(textoMuestra);
        root.getChildren().add(nombrePagina);
    }

    public void posicion() {
        enviar.setLayoutX(480);
        enviar.setLayoutY(390);
        codigo.setLayoutX(280);
        codigo.setLayoutY(390);
        textoMuestra.setLayoutX(280);
        textoMuestra.setLayoutY(440);
        nombrePagina.setLayoutX(120);
        nombrePagina.setLayoutY(50);
        nombrePagina.setScaleX(2);
        nombrePagina.setScaleY(3);

    }
    
    public static void texto(String valor){
        textoMuestra.setText(valor);
    }
}
