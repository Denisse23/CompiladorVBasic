/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.treeelements;

import java.util.ArrayList;

/**
 *
 * @author Denisse
 */
public class bloque_function extends bloque {
    identificador identificador;
    ArrayList<parametro> parametros;
    ArrayList<declaracion> declaraciones;
    tipovar tipo;
    public bloque_function(identificador id,ArrayList<parametro> p , tipovar t,  ArrayList<declaracion> d  ){
       declaraciones = d;
       parametros = p;
       identificador = id;
       tipo = t;
    }
    public bloque_function(identificador id,ArrayList<parametro> p ,   ArrayList<declaracion> d  ){
       declaraciones = d;
       parametros = p;
       identificador = id;
    }
    public bloque_function(identificador id, tipovar t, ArrayList<declaracion> d ){
       declaraciones = d;
       identificador = id;
       parametros = new ArrayList();
       tipo = t;
    }
    public bloque_function(identificador id,  ArrayList<declaracion> d ){
       declaraciones = d;
       identificador = id;
       parametros = new ArrayList();
    }
    
    
    @Override
    public String toString(int nivel) {
       String tabs = "";
       for(int i =0; i<nivel;i++){
           tabs +=  "\t";
       }
       String re = "\n"+tabs+"bloque_function"+identificador.toString(nivel+1);
       if(parametros!=null && parametros.size()>0){
           re+= "\n"+tabs+"\t"+"parametros";
       for(int i =0; i<parametros.size();i++){
           re +=  parametros.get(i).toString(nivel+2);
       }
       }
       if(tipo!=null){
            re += tipo.toString(nivel+1);
       }
       if(declaraciones!=null && declaraciones.size()>0){
       re += "\n"+tabs+"\t"+"declaraciones";
       for(int i =0; i<declaraciones.size();i++){
           re +=  declaraciones.get(i).toString(nivel+2);
       }
       }
       return re;
    }
}
