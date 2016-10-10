/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.treeelements;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Denisse
 */
public class Node {
    
    String val;
    ArrayList<Node> hijos;
   
    public Node(String v ){
        val= v;
        hijos = new ArrayList();
    }
    
    public void addNode(Node n){
        hijos.add(n);
    }
    
    public void addListNode(ArrayList<Node> nodos){
            hijos.addAll(nodos);
    }
    
    public void setNodes(ArrayList<Node> nodos){
        hijos = nodos;
    }
    
    public String getVal(){
        return val;
    }
    
    public boolean isLeaf(){
        if(hijos.size()==0)
            return true;
        else
            return false;
    }
    
    public void toNode(DefaultMutableTreeNode dmtn){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(val);
        dmtn.add(nodo);
        if(!isLeaf()){
            
            for(int i=hijos.size()-1;i>=0;i--)
                hijos.get(i).toNode(nodo);
        }
   }
}
