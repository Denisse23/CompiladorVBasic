/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.CodigoIntermedio;

import java.util.ArrayList;

/**
 *
 * @author Denisse
 */
public class Cuadruplos {

    ArrayList<CuadruploRow> rows;
    private String temporal;
    private String etiqueta;

    public Cuadruplos() {
        rows = new ArrayList();
        temporal = "t0";
        etiqueta = "ETIQUE0";
    }

    public ArrayList<CuadruploRow> getRows() {
        return rows;
    }

    public void setRows(ArrayList<CuadruploRow> rows) {
        this.rows = rows;
    }

    public void generarCuadruplo(String operador, String argumento1, String argumento2, String respuesta) {
        rows.add(new CuadruploRow(operador, argumento1, argumento2, respuesta));
    }

    private int getNextTemporal() {
        return Integer.parseInt(temporal.substring(1)) + 1;
    }

    public String temporalNuevo() {
        temporal = temporal.substring(0, 1) + getNextTemporal();
        return temporal;
    }

    private int getNextEtiqueta() {
        return Integer.parseInt(etiqueta.substring(6)) + 1;
    }

    public String etiquetaNueva() {
        etiqueta = etiqueta.substring(0, 6) + getNextEtiqueta();
        return etiqueta;
    }

    public String getLastTemp() {
        return temporal;
    }

    public String getLastEtiqueta() {
        return etiqueta;
    }

    ///////////////////////////////////////////////////////////////////METODOS PARA CODIGO FINAL//////////////////////////////////////////////////////
    public ArrayList<String> getMensajes() {
        ArrayList<String> mensajes = new ArrayList();
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getOperador().equals("PRINT") && rows.get(i).getArgumento1().contains("\"")) {
                mensajes.add(rows.get(i).getArgumento1());
            }
        }
        return mensajes;
    }
    
    public int get_num_linea_sig_func(int anteriornum) {
        int line = anteriornum;
        for (int i = anteriornum; i < rows.size(); i++) {
            if (rows.get(i).getOperador().equals("ETIQUEFUNC")) {
                line = i;
                break;
            }
        }
        return line;
    }
    
     public int get_num_linea_fin_func(String namefunc) {
        int line = 0;
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getOperador().equals("FINETIQUEFUNC") && rows.get(i).getArgumento1().equals(namefunc)) {
                line = i;
                break;
            }
        }
        return line;
    }

}
