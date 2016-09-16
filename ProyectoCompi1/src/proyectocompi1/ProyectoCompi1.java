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
        String path = ("./src/proyectocompi1/Vbasic.flex"); 
        try{
            generarLexer(path);
        }catch(Exception e){}
        String opciones[] = new String[5];
        //Seleccionamos la opción de dirección de destino
        opciones[0] = "-destdir";
        //Le damos la dirección
        opciones[1] = "src\\proyectocompi1\\";
        //Seleccionamos la opción de nombre de archivo
        opciones[2] = "-parser";
        //Le damos el nombre que queremos que tenga
        opciones[3] = "parser";
        //Le decimos donde se encuentra el archivo .cup
        opciones[4] = "src\\proyectocompi1\\Vbasic.cup";
        try {
            java_cup.Main.main(opciones);
        } catch (Exception e) {
            System.out.print(e);
        }
        Ventana vent = new Ventana();
        vent.setVisible(true);
        
    }

    private static void generarLexer(String path) {
            File file= new File(path);
            jflex.Main.generate(file);
    }
    
}
