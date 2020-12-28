/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
        
        private String evento;
        private double probabilidad;
        
        public Node(E data) {
            this.data = data;
        }
        
        public Node(E data, double posX, double posY) {
            this.hVentana = 600/2;
            this.data = data;
            this.posX = posX;
            this.posY = posY;
            circulo = new Circle(5);
            circulo.setLayoutX(posX);
            circulo.setLayoutY(posY);
        }
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public boolean add(E child, E parent) {
        Node<E> nchild = new Node<>(child, 400, 50);
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
    
    public E decision(List<String> lado) {
        Node<E> resultado = decision(new LinkedList<>(lado), root);
        return resultado != null ? resultado.data : (E) "Incertidumbre";
    }
    
    private Node<E> decision(Queue<String> lado, Node<E> n) {
        String l = "";
        if (!lado.isEmpty()) {
            l = lado.poll();
        }
        if (n == null) {
            return null;
        } else if (l.equals("YES")) {
            return decision(lado, n.right);
        } else if (l.equals("NO")) {
            return decision(lado, n.left);
        } else if (n.left == null && n.right == null) {
            return n;
        }
        return null;
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
        return añadirMorse(letra, new LinkedList<>(path), root);
    }
    
    private boolean añadirMorse(String letra, Queue<String> path, Node<E> n) {
        String signo = "";
        if (!path.isEmpty()) {
            signo = path.poll();
        }
        switch (signo) {
            case ".":
                if (n.right == null) {
                    int nivel = (int) (n.posY/50);
                    n.right = new Node<>((E) signo, n.posX + (n.hVentana/(nivel * 2)), n.posY + 50);
                    dibujarNodo(n.right);
                    dibujarLinea(n, n.right);
                    System.out.println("Derecha");
                }
                return añadirMorse(letra, path, n.right);
            case "-":
                if (n.left == null) {
                    int nivel = (int) (n.posY/50);
                    n.left = new Node<>((E) signo, n.posX - (n.hVentana/(nivel * 2)), n.posY + 50);
                    dibujarNodo(n.left);
                    dibujarLinea(n, n.left);
                    System.out.println("Izquierda");
                }
                return añadirMorse(letra, path, n.left);
            default:
                n.data = (E) letra;
                break;
        }
        return true;
    }
    
    private void dibujarNodo(Node<E> n){
        App.agregarNodo(n.circulo);
    }
    
    private void dibujarLinea(Node<E> ini, Node<E> fin) {
        Line line = new Line();
        int offset = 6;
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
    
    public double estimarProbabilidad(String palabra) {
        return estimarProbabilidad(palabra, root);
    }
    
    private double estimarProbabilidad(String palabra, Node<E> n) {
        if (n == null) {
            return 0;
        } else if (n.evento.equals(palabra)) {
            return n.probabilidad;
        } else {
            return n.probabilidad * (estimarProbabilidad(palabra, n.left) + estimarProbabilidad(palabra, n.right));
        }
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
