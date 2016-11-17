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
public class Programa extends Node {
    
    public Programa(String v) {
        super(v);
    }
    

   
    public DefaultMutableTreeNode toNodeP(){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(val);
        if(!isLeaf()){
            
            for(int i=hijos.size()-1;i>=0;i--)
                hijos.get(i).toNode(nodo);
        }
        
        return nodo;
   }
    
}
