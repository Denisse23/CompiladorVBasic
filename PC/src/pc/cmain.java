/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import pc.treeelements.*;
/**
 *
 * @author Denisse
 */
public class cmain {
        public static void main(String[] args) {
    String opciones[] = new String[5];
        //Seleccionamos la opción de dirección de destino
        opciones[0] = "-destdir";
        //Le damos la dirección
        opciones[1] = "src\\PC\\";
        //Seleccionamos la opción de nombre de archivo
        opciones[2] = "-parser";
        //Le damos el nombre que queremos que tenga
        opciones[3] = "Vbasicsintaxis";
        //Le decimos donde se encuentra el archivo .cup
        opciones[4] = "src\\PC\\Vbasic_sintaxis.cup";
        
        
        String opciones1[] = new String[5];
        //Seleccionamos la opción de dirección de destino
        opciones1[0] = "-destdir";
        //Le damos la dirección
        opciones1[1] = "src\\PC\\";
        //Seleccionamos la opción de nombre de archivo
        opciones1[2] = "-parser";
        //Le damos el nombre que queremos que tenga
        opciones1[3] = "Vbasictipos";
        //Le decimos donde se encuentra el archivo .cup
        opciones1[4] = "src\\PC\\Vbasic.cup";
        try {
            java_cup.Main.main(opciones);
            java_cup.Main.main(opciones1);
        } catch (Exception e) {
            System.out.print(e);
        }
            
        }
        
        
}
