/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.cuadruplos;

/**
 *
 * @author Denisse
 */
public class CuadruploRow {
    private String operador;
    private String argumento1;
    private String argumento2;
    private String resputa;
    
    public CuadruploRow(String operador, String argumento1, String argumento2, String respuesta){
        this.operador = operador;
        this.argumento1 = argumento1;
        this.argumento2 = argumento2;
        this.resputa = respuesta;
        
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getArgumento1() {
        return argumento1;
    }

    public void setArgumento1(String argumento1) {
        this.argumento1 = argumento1;
    }

    public String getArgumento2() {
        return argumento2;
    }

    public void setArgumento2(String argumento2) {
        this.argumento2 = argumento2;
    }

    public String getResputa() {
        return resputa;
    }

    public void setResputa(String resputa) {
        this.resputa = resputa;
    }
    
    
}
