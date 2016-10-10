/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.treeelements;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Denisse
 */
public class Stmt_Funcion extends Node{
    public Stmt_Funcion(String v) {
        super(v);
    }
    
    public Node getIdentificador(){
        try{
            return hijos.get(0);
        }catch(Exception e){
            return null;
        }
    }
    public Node getParameters(){
        try{
            return hijos.get(1);
        }catch(Exception e){
            return null;
        }
    }
    
    public Node getType(){
        try{
            return hijos.get(2);
        }catch(Exception e){
            return null;
        }
    }
    
    public Node getBody(){
        try{
            return hijos.get(3);
        }catch(Exception e){
            return null;
        }
    }
    
    @Override
    public void toNode(DefaultMutableTreeNode dmtn){
            DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(val);
            dmtn.add(nodo);
            getIdentificador().toNode(nodo);
            if(!getParameters().isLeaf())
                getParameters().toNode(nodo);
            getType().toNode(nodo);
            if(!getBody().isLeaf())
                getBody().toNode(nodo);
        
   }
}
