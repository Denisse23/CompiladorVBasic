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
public class Funcion extends Tipo{
    
    private Tipo retorno;
    private ArrayList<Tipo> Params;
    
    public Funcion(){
        this.setName("Proc");
        Params = new ArrayList();
        retorno = new voidt();
    }
    
    public void setRetorno(Tipo r){
        retorno = r;
    }
    
    public Tipo getRetorno(){
        return retorno;
    }
    
    public void addParametro(Tipo p){
        Params.add(p);
    }
    
    public String toStringSinRetorno(){
        String con ="";
        if(Params.size()==0){
            con="Void";
        }
        for(int i=0;i<Params.size();i++){
            if(i==0)
                con+=Params.get(i).toString();
            else
                con+= "X"+Params.get(i).toString();
        }
        
        return con;
        
    }
    
    @Override
    public String toString(){
        String con ="";
        if(Params.size()==0){
            con="Void";
        }
        for(int i=0;i<Params.size();i++){
            if(i==0)
                con+=Params.get(i).toString();
            else
                con+= "X"+Params.get(i).toString();
        }
        
        return con+"->"+retorno.toString();
        
    }
    
    
}
