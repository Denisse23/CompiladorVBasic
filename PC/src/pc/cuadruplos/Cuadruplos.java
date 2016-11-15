/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.cuadruplos;

import java.util.ArrayList;

/**
 *
 * @author Denisse
 */
public class Cuadruplos {
    ArrayList<CuadruploRow> rows;
    private String temporal;
    
    public Cuadruplos(){
        rows = new ArrayList();
        temporal = "t0";
    }

        
    public ArrayList<CuadruploRow> getRows() {
        return rows;
    }

    public void setRows(ArrayList<CuadruploRow> rows) {
        this.rows = rows;
    }
    
    public void generarCuadruplo(String operador, String argumento1, String argumento2, String respuesta){
        rows.add(new CuadruploRow(operador, argumento1, argumento2, respuesta));
    }
    
    private int getNextTemporal(){
        return Integer.parseInt(temporal.substring(1)) + 1;
    }
    
    public String temporalNuevo(){
        temporal = temporal.substring(0, 1) + getNextTemporal();
        return temporal ;
    }
}
