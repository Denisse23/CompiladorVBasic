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
public class Stmt_If extends Node{
    public Stmt_If(String v) {
        super(v);
    }
    
    public Node getCondition(){
        try{
            return hijos.get(0);
        }catch(Exception e){
            return null;
        }
    }
    public Node getBody(){
        try{
            return hijos.get(1);
        }catch(Exception e){
            return null;
        }
    }
    
    public Node getElseBody(){
        try{
            return hijos.get(2);
        }catch(Exception e){
            return null;
        }
    }
    @Override
    public  ArrayList<Node> getHijos(){
        ArrayList<Node> h = new ArrayList();
        if(!isLeaf()){
            h.add(getCondition());
            h.add(getBody());
            h.add(getElseBody());
        }
        
        return h;
   }
    
    @Override
    public void toNode(DefaultMutableTreeNode dmtn){
            DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(val);
            dmtn.add(nodo);
            getCondition().toNode(nodo);
            if(!getBody().isLeaf())
                getBody().toNode(nodo);
            if(!getElseBody().isLeaf())
                getElseBody().toNode(nodo);
        
   }
    
}
