/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocompi1;

import java.io.File;
import java.io.FileReader;

/**
 *
 * @author kingdomkaz
 */
public class ProyectoCompi1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       // String path = ("C:/Users/kingdomkaz/Documents/Analisis de Algoritmos/ProyectoCompi1/build/classes/proyectocompi1/Vbasic.flex");       
        //generarLexer(path);
        Ventana vent = new Ventana();
        vent.setVisible(true);
        
        
        
    }

    private static void generarLexer(String path) {
            File file= new File(path);
            jflex.Main.generate(file);
    }
    
}
