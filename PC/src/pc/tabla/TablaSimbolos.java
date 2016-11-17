/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.tabla;

import java.util.ArrayList;

/**
 *
 * @author Denisse
 */
public class TablaSimbolos {
    public String name;
    private ArrayList<Token> tokens;
    
    public TablaSimbolos(String name){
        this.name = name;
        tokens = new ArrayList();
    }
    
    public void add_id(Token t){
        tokens.add(t);
    }
    
    public ArrayList<Token> get_ids(){
        return tokens;
    }
    
    public Token get_id(String id){
        for(int i=0;i<tokens.size();i++){
            if(tokens.get(i).getId().equals(id)){
                return tokens.get(i);
            }
        }
        return null;
    }
    
}
