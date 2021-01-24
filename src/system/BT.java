/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.App;

/**
 *
 * @author El Pitagoras
 */
public class BT<E> {

    private Node<E> root;
    private double hVentana = (double)800/2;
    private Sonido sonido;
    private HashMap<String, List<String>> mapaMorse = (HashMap<String,List<String>>)leerTraducciones();

    private class Node<E> {

        private E data;
        private Node<E> left;
        private Node<E> right;

        private Circle circulo;
        
        private double posX;
        private double posY;
        private Text evento;

        public Node(E data) {
            this.data = data;
        }

        public Node(E data, double posX, double posY) {
            this.data = data;
            this.posX = posX;
            this.posY = posY;
            circulo = new Circle(posX + 5, posY - 5, 10);
            circulo.setStroke(Color.BLACK);
            evento = new Text((String) data);
            evento.setLayoutX(posX);
            evento.setLayoutY(posY);
            evento.setTextAlignment(TextAlignment.CENTER);
            evento.setFill(Color.WHITE);

        }
    }
    
    public BT() {
        sonido = new Sonido();
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean add(E child, E parent) {
        Node<E> nchild = new Node<>(child, hVentana, 50);
        nchild.evento.setLayoutX(nchild.posX - 10);
        nchild.circulo.setScaleX(3);
        if (isEmpty() && parent == null) {
            root = nchild;
            dibujarNodo(root);
            return true;
        }
        Node<E> np = searchNode(parent);
        Node<E> nce = searchNode(child);
        if (nce == null && np != null) {
            if (np.left == null) {
                np.left = nchild;
                dibujarNodo(nchild);
                return true;
            } else if (np.right == null) {
                np.right = nchild;
                dibujarNodo(nchild);
                return true;
            }
        }
        return false;
    }
    public int size() {
        return size(root);
    }

    private int size(Node<E> n) {
        if (n == null) {
            return 0;
        }
        return 1 + size(n.left) + size(n.right);
    }

    public int height() {
        return height(root);
    }

    private int height(Node<E> n) {
        if (n == null) {
            return 0;
        }

        return 1 + Math.max(height(n.left), height(n.right));
    }

    private Node<E> searchNode(E data) {
        return searchNode(data, root);
    }

    private Node<E> searchNode(E data, Node<E> p) {
        if (p == null) {
            return p;
        } else if (p.data.equals(data)) {
            return p;
        } else {
            Node<E> rl = searchNode(data, p.left);
            if (rl != null) {
                return rl;
            }
            return searchNode(data, p.right);
        }
    }

    public Node<E> searchParent(E data) {
        return searchParent(data, root);
    }

    private Node<E> searchParent(E data, Node<E> n) {
        if (n == null) {
            return n;
        } else if ((n.left != null && n.left.data.equals(data)) || (n.right != null && n.right.data.equals(data))) {
            return n;
        } else {
            Node<E> rl = searchParent(data, n.left);
            if (rl != null) {
                return rl;
            }
            return searchParent(data, n.right);
        }
    }
    
    public String codificarMorse(Queue<String> codigos) {
        return codificarMorse(codigos, root);
    }
    
    private String codificarMorse(Queue<String> codigos, Node<E> n) {
        String c = "";
        if (!codigos.isEmpty()) {
            c = codigos.poll();
        }
        if (n == null) {
            return "SIN COINCIDENCIA";
        } else if (c.equals("-")) {
            return codificarMorse(codigos, n.left);
        } else if (c.equals(".")) {
            return codificarMorse(codigos, n.right);
        } 
        return (String) n.data;
    }

    public boolean addMorse(String letra, List<String> path) {
        add((E) "Inicio", null);
        return addMorse(letra, new LinkedList<>(path), root, 29);
    }

    private boolean addMorse(String letra, Queue<String> path, Node<E> n, int niv) {
        String signo = "";
        if (!path.isEmpty()) {
            signo = path.poll();
        }
        niv -= 1;
        switch (signo) {
            case ".":
                if (n.right == null) {
                    int nivel = (int) (n.posY / 50);
                    n.right = new Node<>((E) letra, n.posX + (hVentana / (nivel * 2)) - niv, n.posY + 55);
                    dibujarLinea(n, n.right);
                    
                }
                return addMorse(letra, path, n.right, niv);
            case "-":
                if (n.left == null) {
                    int nivel = (int) (n.posY / 50);
                    n.left = new Node<>((E) letra, n.posX - (hVentana / (nivel * 2)) + niv, n.posY + 55);
                    dibujarLinea(n, n.left);
                    
                }
                return addMorse(letra, path, n.left, niv);
            default:
                n.data = (E) letra;
                n.evento.setText(n.data.toString());
                dibujarNodo(n);
                break;
        }
        return true;
    }

    public Map<String, List<String>> leerTraducciones() {
        HashMap<String, List<String>> mapMorse = new HashMap<>();
        try (Scanner sc = new Scanner(new File("traducciones.txt"))) {
            while (sc.hasNextLine()) {
                String[] codigoMorse = sc.nextLine().split("\\|");
                List<String> morse = new LinkedList<>();
                String codigo = codigoMorse[1].replace(" ", "");
                for (int i = 0; i < codigo.length(); i++) {
                    char c = codigo.charAt(i);
                    morse.add(c + "");
                }
                mapMorse.put(codigoMorse[0], morse);
            }
        } catch (FileNotFoundException ex) { 
            Logger.getLogger(BT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapMorse;
    }

    private void dibujarNodo(Node<E> n) {
        App.agregarNodo(n.circulo);
        App.agregarNodo(n.evento);
    }

    private void dibujarLinea(Node<E> ini, Node<E> fin) {
        Line line = new Line();
        int offset = 2;
        double difX = Math.abs(ini.posX - fin.posX);
        double difY = Math.abs(fin.posY - ini.posY);
        double ang = Math.atan(difY / difX);

        if (fin.equals(ini.left)) {
            line.setStartX(ini.posX - offset);
            line.setEndX(fin.posX + offset);
            line.setStroke(Color.RED);
        } else {
            line.setStartX(ini.posX + offset);
            line.setEndX(fin.posX - offset);
            line.setStroke(Color.BLUE);
        }
        line.setStartY(fin.posY - (difX - offset) * Math.tan(ang));
        line.setEndY(fin.posY - (offset * Math.tan(ang)));
        App.agregarNodo(line);
    }
    
    public void llenarArbol(){
        for (Map.Entry<String, List<String>> dato : mapaMorse.entrySet()) {
            this.addMorse(dato.getKey(), dato.getValue());
        }
    }



    public void reiniciarColor() {
        root.circulo.setFill(Color.BLACK);
        reiniciarColor(root);
    }
    
    private void reiniciarColor(Node<E> n) {
        if (n != null) {
            n.circulo.setFill(Color.BLACK);
            reiniciarColor(n.left);
            reiniciarColor(n.right);
        }
    }
    
    
    
    public void pintar(String valor) {
        Thread h1 = new Thread(new HiloPintar(valor));
        h1.start();
    }
    
    private class HiloPintar implements Runnable {

        private String valor;
        
        public HiloPintar(String valor) {
            this.valor = valor;
        }
        private void mostrarCamino(Queue<String> codigos) throws InterruptedException {
        mostrarCamino(codigos, root);
    }
    
    private void mostrarCamino(Queue<String> codigos, Node<E> n) throws InterruptedException {
        String signo = "";
        if (!codigos.isEmpty()) {
            signo = codigos.poll();
        }
        if (n != null) {
            n.circulo.setFill(Color.BLUE);
            sleep(1500);
            if (signo.equals("-")) {
                sonido.play("raya");
                mostrarCamino(codigos, n.left);
            } else if (signo.equals(".")) {
                sonido.play("punto");
                mostrarCamino(codigos, n.right);
            }
        }
    }
        @Override
        public void run() {
            for (int i = 0; i < valor.length(); i++) {
                try {
                    reiniciarColor();
                    List<String> codigo = mapaMorse.get(valor.charAt(i)+"");
                    mostrarCamino(listaCodes(codigo));
                    
                    App.texto("El código morse es: "+codigo.toString().replace("[", "").replace("]","").replace(","," "));
                } catch (InterruptedException ex) {
                    Logger.getLogger(BT.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        public Queue<String> listaCodes(List<String> valor) {
            Queue<String> cola = new LinkedList<>();
            cola.addAll(valor);
            return cola;
        }
        
        
    }
}
