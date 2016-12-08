/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.treeelements;

import pc.Tipos.*;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Denisse
 */
public class Node {
    
    String val;
    ArrayList<Node> hijos;
    Tipo tipo_tabla;
    boolean typeIdentificador;
    public String siguiente;
    public String verdadera;
    public String falsa;
    public String comienzo;
   
    public Node(String v ){
        val= v;
        hijos = new ArrayList();
        tipo_tabla = new voidt();
        typeIdentificador = false;
        siguiente = "";
        verdadera = "";
        falsa = "";
    }
    
    public void addNode(Node n){
        hijos.add(n);
    }
    
    public void addListNode(ArrayList<Node> nodos){
            hijos.addAll(nodos);
    }
    
    public ArrayList<Node> getListNode(){
            return hijos;
    }
    
    public void setNodes(ArrayList<Node> nodos){
        hijos = nodos;
    }
    
    public String getVal(){
        return val;
    }

    public Tipo getTipo_tabla() {
        return tipo_tabla;
    }

    public void setTipo_tabla(Tipo tipo_tabla) {
        this.tipo_tabla = tipo_tabla;
    }
    
    
    
    public boolean isLeaf(){
        if(hijos.size()==0)
            return true;
        else
            return false;
    }

    public boolean isTypeIdentificador() {
        return typeIdentificador;
    }

    public void setTypeIdentificador(boolean typeIdentificador) {
        this.typeIdentificador = typeIdentificador;
    }
    
    public  ArrayList<Node> getHijos(){
        ArrayList<Node> h = new ArrayList();
        if(!isLeaf()){
            //////////////se envian al reves, porque la entrada al arbol fue en orden inverso 
            for(int i=hijos.size()-1;i>=0;i--)
                h.add(hijos.get(i));
        }
        
        return h;
   }
    
    
    
    
    
    public void toNode(DefaultMutableTreeNode dmtn){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(val);
        dmtn.add(nodo);
        if(!isLeaf()){
            //////////////se envian al reves, porque la entrada al arbol fue en orden inverso 
            for(int i=hijos.size()-1;i>=0;i--)
                hijos.get(i).toNode(nodo);
        }
   }
}
