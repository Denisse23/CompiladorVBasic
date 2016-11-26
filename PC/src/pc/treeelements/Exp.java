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
public class Exp extends Node {
    
    public Exp(String v) {
        super(v);
    }
    
    public Node getLeft(){
         try{
            return hijos.get(0);
        }catch(Exception e){
            return null;
        }
    }
    
    public Node getRight(){
        try{
            return hijos.get(1);
        }catch(Exception e){
            return null;
        }
    }
    
    @Override
    public  ArrayList<Node> getHijos(){
        ArrayList<Node> h = new ArrayList();
        if(!isLeaf()){
            h.add(getLeft());
            h.add(getRight());
        }
        
        return h;
   }
    @Override
    public void toNode(DefaultMutableTreeNode dmtn){
            DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(val);
            dmtn.add(nodo);
            if(!isLeaf()){
                getLeft().toNode(nodo);
                getRight().toNode(nodo);
            }
        
   }
    
}
