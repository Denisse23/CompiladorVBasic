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
public class programa {
    public ArrayList<bloque> bloques;
    public ArrayList<declaracion> declaraciones;
    
    public programa(){
        bloques = new ArrayList();
        declaraciones = new ArrayList();
        
    }
    
    public void addBloque(bloque b){
        bloques.add(b);
    }
    
    public void addDeclaracion(declaracion d){
        declaraciones.add(d);
    }
    
   
    public String toString() {
       String re="";
       re += "Programa\n\tbloques";
       if(bloques.size()>0){
       
       for(int i =0; i<bloques.size();i++){
           re +=  bloques.get(i).toString(1+1);
       }
       }
       if(declaraciones.size()>0){
       re += "\n\tdeclaraciones";
       for(int i =0; i<declaraciones.size();i++){
           re +=  declaraciones.get(i).toString(1+1);
       }
       }
       return re;
    }
}
