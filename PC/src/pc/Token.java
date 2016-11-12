/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc;

import pc.Tipos.Tipo;

/**
 *
 * @author Denisse
 */
public class Token {
    private String id;
    private Tipo tipo;
    private int linea;
    private int columna;
    private String ambito;
    private int offset;
    
    public Token(String id,Tipo tipo, int linea, int columna, String ambito, int offset){
        this.id = id;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.ambito = ambito;
        this.offset = offset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    
}
