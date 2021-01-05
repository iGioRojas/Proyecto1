/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import main.App;

/**
 *
 * @author El Pitagoras
 */
public class BT<E> {
    
    private Node<E> root;
    
    private class Node<E> {
        
        private E data;
        private Node<E> left;
        private Node<E> right;
        
        private Circle circulo;
        private double hVentana;
        private double posX;
        private double posY;
        
        private Text evento;        
        
        public Node(E data) {
            this.data = data;
        }
        
        public Node(E data, double posX, double posY) {
            this.hVentana = 600/2;
            this.data = data;
            this.posX = posX;
            this.posY = posY;
            circulo = new Circle();
            circulo.setLayoutX(posX+5);
            circulo.setLayoutY(posY-4);
            circulo.setScaleX(20);
            circulo.setScaleY(20);
            circulo.setStroke(Color.BLACK);
            evento = new Text((String) data);
            evento.setLayoutX(posX);
            evento.setLayoutY(posY);
            evento.setFill(Color.WHITE);
            
        }
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public boolean add(E child, E parent) {
        Node<E> nchild = new Node<>(child, 300, 50);
        nchild.evento.setLayoutX(nchild.posX-10);
        nchild.circulo.setScaleX(50);
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
    
    public boolean remove(E data) {
        Node<E> p = searchNode(data);
        if (p == null) {
            return false;
        }
        if (p.left != null && p.left.data.equals(data)) {
            p.left = null;
        } else {
            p.right = null;
        }
        return true;
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
    
    public int countLeaves() {
        return countLeaves(root);
    }
    
    private int countLeaves(Node<E> n) {
        if (n == null) {
            return 0;
        } else if (n.left == null && n.right == null) {
            return 1;
        } else {
            return countLeaves(n.left) + countLeaves(n.right);
        }
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
            return codificarMorse(codigos);
        } else if (!n.data.equals("-") && !n.data.equals(".")) {
            return n.data + codificarMorse(codigos);
        } else if (c.equals("-")) {
            return codificarMorse(codigos, n.left);
        } else if (c.equals(".")) {
            return codificarMorse(codigos, n.right);
        } 
        return "";
    }
    
    public boolean añadirMorse(String letra, List<String> path) {
        add((E) "Inicio", null);
        return añadirMorse(letra, new LinkedList<>(path), root,29);
    }
    
    private boolean añadirMorse(String letra, Queue<String> path, Node<E> n,int niv) {
        String signo = "";
        if (!path.isEmpty()) {
            signo = path.poll();
        }
        niv-=1;
        switch (signo) {
            case ".":
                if (n.right == null) {
                    int nivel = (int) (n.posY/50);
                    n.right = new Node<>((E) letra, n.posX + (n.hVentana/(nivel * 2)) - niv, n.posY + 55);
                    dibujarLinea(n, n.right); 
                }
                return añadirMorse(letra, path, n.right,niv);
            case "-":
                if (n.left == null) {
                    int nivel = (int) (n.posY/50);
                    n.left = new Node<>((E) letra, n.posX - (n.hVentana/(nivel * 2))+niv, n.posY + 55);
                    dibujarLinea(n, n.left);

                }
                return añadirMorse(letra, path, n.left,niv);
            default:
            n.data = (E) letra;
            n.evento.setText(n.data.toString());
            dibujarNodo(n);
            break;
        }
        return true;
    }    
    public HashMap<String,List<String>> leerTraducciones(){
        HashMap<String,List<String>> mapaMorse = new HashMap<>();
        try (Scanner sc = new Scanner(new File("traducciones.txt"))) {
            while (sc.hasNextLine()) {
                String[] codigoMorse = sc.nextLine().split("\\|");
                List<String> morse = new LinkedList<>();
                String codigo = codigoMorse[1].replace(" ","");
                for (int i = 0; i < codigo.length(); i++) {
                    char c = codigo.charAt(i);
                    morse.add(c+"");
                }
                mapaMorse.put(codigoMorse[0],morse);
            }
        } catch (Exception e) {
            System.out.println("No hay codes");
        }
        System.out.println(mapaMorse.entrySet());
        System.out.println(mapaMorse.size());
        return mapaMorse;
    }
    
    private void dibujarNodo(Node<E> n){
        App.agregarNodo(n.circulo);
        App.agregarNodo(n.evento);
    }
    
    private void dibujarLinea(Node<E> ini, Node<E> fin) {
        Line line = new Line();
        int offset = 1;
        double difX = Math.abs(ini.posX - fin.posX);
        double difY = Math.abs(fin.posY - ini.posY);
        double ang = Math.atan(difY/difX);
        
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
    
    
    public void anchura() {
        if (!isEmpty()) {
            Queue<Node<E>> cola = new LinkedList<>();
            cola.offer(root);
            while (!cola.isEmpty()) {
                Node<E> n = cola.poll();
                System.out.print(n.data);
                if (n.left != null) {
                    cola.offer(n.left);
                }
                if (n.right != null) {
                    cola.offer(n.right);
                }
            }
        }
        System.out.println("");
    }
    
    public void postOrden() {
        postOrden(root);
    }
    
    private void postOrden(Node<E> p) {
        if (p != null) {
            postOrden(p.left);
            postOrden(p.right);
            System.out.print(p.data);
        }
    }
}
