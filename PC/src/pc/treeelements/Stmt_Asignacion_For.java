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
public class Stmt_Asignacion_For extends Node{
    
    public Stmt_Asignacion_For(String v) {
        super(v);
    }
    
    public Node getIdentificadorOVar(){
         try{
            return hijos.get(0);
        }catch(Exception e){
            return null;
        }
    }
    
    public Node getExp(){
        try{
            return hijos.get(1);
        }catch(Exception e){
            return null;
        }
    }
    @Override
    public void toNode(DefaultMutableTreeNode dmtn){
            DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(val);
            dmtn.add(nodo);
            getIdentificadorOVar().toNode(nodo);
            getExp().toNode(nodo);
        
   }
    
}
