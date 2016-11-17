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
public class Producto extends Tipo {
    private ArrayList<Tipo> Args;
    public Producto(){
        this.setName("Producto");
        Args = new ArrayList();
    }
    
     public void addArgumento(Tipo a){
        Args.add(a);
    }
     
    @Override
    public String toString(){
        String con ="";
        for(int i=0;i<Args.size();i++){
            if(i==0)
                con+=Args.get(i).toString();
            else
                con+= "X"+Args.get(i).toString();
        }
        
        return con;
        
    }
}
