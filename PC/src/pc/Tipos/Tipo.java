/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.Tipos;

/**
 *
 * @author Denisse
 */
public class Tipo {
    private String name;
    private int tamano;
    public Tipo(){
    }
    
    public Tipo(String n){
        name=n;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String n) {
        name = n;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
    
    
    
    
    @Override
    public String toString(){
        return name;
    }
    
    
}
