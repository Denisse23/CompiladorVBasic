/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.Tipos;

import java.util.ArrayList;

/**
 *
 * @author Denisse
 */
public class Registro extends Tipo{
    private ArrayList<String> vars;
    public Registro(){
       this.setName("Record");
    }
    
    public void addVar(String id, Tipo p){
        vars.add("("+id +"X"+p.toString()+")");
    }
    
    @Override
    public String toString(){
        String con ="record(";
        for(int i=0; i<vars.size();i++){
            if(i==0)
                con+=vars.size();
            else
                con+="X"+vars.size();
        }
        return con+")";
    }
    
    
}
