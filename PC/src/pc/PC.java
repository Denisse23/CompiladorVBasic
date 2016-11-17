/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc;

import java.io.File;

/**
 *
 * @author Denisse
 */
public class PC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String path = ("./src/PC/Vbasic.flex"); 
        try{
            generarLexer(path);
        }catch(Exception e){}
        
        
        
    }

    private static void generarLexer(String path) {
            File file= new File(path);
            jflex.Main.generate(file);
    }
    
}
