/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.tabla;

import java.util.ArrayList;
import pc.Tipos.Tipo;

/**
 *
 * @author Denisse
 */
public class TablasDeSimbolos {

    public ArrayList<TablaSimbolos> tablas;

    public TablasDeSimbolos() {
        tablas = new ArrayList();
    }

    public void addTabla(TablaSimbolos ts) {
        tablas.add(ts);
    }

    public boolean existePrincipal() {
        for (int i = 0; i < tablas.size(); i++) {
            if (tablas.get(i).name.equals("Principal")) {
                return true;
            }
        }
        return false;
    }

    public TablaSimbolos getTabla(String name) {
        for (int i = 0; i < tablas.size(); i++) {
            if (tablas.get(i).name.equals(name)) {
                return tablas.get(i);
            }
        }
        return null;
    }

    public boolean existe_id(String id, String ambito, String nametabla) {
        TablaSimbolos t = getTabla(nametabla);
        for (int j = 0; j < t.get_ids().size(); j++) {
            if (t.get_ids().get(j).getId().equals(id) && t.get_ids().get(j).getAmbito().equals(ambito)) {
                return true;
            }

        }

        return false;
    }

    public boolean existe_id_var_proc(String id, String ambito, String nametabla) {
        TablaSimbolos t = getTabla(nametabla);
        for (int j = 0; j < t.get_ids().size(); j++) {
            if ((t.get_ids().get(j).getId().equals(id) && t.get_ids().get(j).getAmbito().equals(ambito))
                    || (t.get_ids().get(j).getId().equals(id) && t.get_ids().get(j).getTipo().getName().equals("Proc"))) {
                return true;
            }

        }

        return false;
    }

    public boolean hay_main() {
        TablaSimbolos t = getTabla("Principal");

        for (int j = 0; j < t.get_ids().size(); j++) {
            if (t.get_ids().get(j).getId().toLowerCase().equals("main") && t.get_ids().get(j).getTipo().getName().equals("Proc")) {
                return true;
            }

        }

        return false;
    }

    public boolean hay_proc_o_fun() {
        TablaSimbolos t = getTabla("Principal");

        for (int j = 0; j < t.get_ids().size(); j++) {
            if (t.get_ids().get(j).getTipo().getName().equals("Proc")) {
                return true;
            }

        }

        return false;
    }

    public String remover(String ambito) {

        String[] partes = ambito.split("\\.");
        String ambito_a = "";
        for (int i = 0; i < partes.length - 1; i++) {
            if (i == 0) {
                ambito_a += partes[i];
            } else {
                ambito_a += "." + partes[i];
            }
        }

        if (partes.length == 0) {
            ambito_a = "";
        }

        return ambito_a;

    }

    public boolean existe_id_ambitos(String id, String ambito) {
        TablaSimbolos t = getTabla("Principal");
        String am = ambito;
        while (!am.equals("")) {
            for (int j = 0; j < t.get_ids().size(); j++) {
                if (t.get_ids().get(j).getId().equals(id) && t.get_ids().get(j).getAmbito().equals(am)
                        && !(t.get_ids().get(j).getTipo().getName().equals("Record"))) {
                    return true;
                }

            }
            am = remover(am);

        }
        return false;
    }

    public Token get_id_ambitos(String id, String ambito, String tabla) {
        TablaSimbolos t = getTabla(tabla);
        String am = ambito;
        while (!am.equals("")) {
            for (int j = 0; j < t.get_ids().size(); j++) {
                if (!ambito.equals("registro")) {
                    if (t.get_ids().get(j).getId().equals(id) && t.get_ids().get(j).getAmbito().equals(am)) {
                        return t.get_ids().get(j);
                    }
                } else if (t.get_ids().get(j).getId().equals(id)) {
                    return t.get_ids().get(j);
                }

            }
            am = remover(am);

        }
        return null;
    }

    public Object[] existe_id_estructura(String id, String ambito) {
        boolean esta = false;
        String var_o_registro = "var";
        String name_registro = "";
        String name_id = "";
        String id_no_encontrada = "";
        String name_registro_pertenece_name_id = "";
        Token id_var = null;
        String[] ids = id.split("\\.");///////// buscar si existen los nombres de variables dentro de los registros(type)
        if (existe_id_ambitos(ids[0], ambito)) {
            var_o_registro = "registro";
            id_var = get_id_ambitos(ids[0], ambito, "Principal");
            for (int i = 1; i < ids.length; i++) {
                if (i == 1) {
                    name_id = id_var.getId();
                    name_registro_pertenece_name_id = "--";
                    name_registro = id_var.getTipo().getName();
                }
                if (getTabla(id_var.getTipo().getName()) != null) {
                    name_registro_pertenece_name_id = id_var.getTipo().getName();
                    if (get_id_ambitos(ids[i], "registro", id_var.getTipo().getName()) != null) {
                        esta = true;
                        id_var = get_id_ambitos(ids[i], "registro", id_var.getTipo().getName());
                        name_id = id_var.getId();
                        name_registro = id_var.getTipo().getName();
                    } else {
                        id_no_encontrada = ids[i];
                        esta = false;
                        break;
                    }
                } else {

                    esta = false;
                    break;
                }

            }

        }

        Object[] o = {esta, var_o_registro, name_registro, name_id, id_no_encontrada, name_registro_pertenece_name_id};
        return o;
    }

////////////////////METODOS A USAR CUANDO YA SE HA COMPROBADO EL AMIBTO Y EXISTENCIA DE LOS IDENTIFICADORES////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String get_ambito_hijos_id(String id) {
        TablaSimbolos t = getTabla("Principal");
        for (int j = 0; j < t.get_ids().size(); j++) {
            if (t.get_ids().get(j).getId().equals(id)) {
                return t.get_ids().get(j).getHijos();
            }

        }

        return "";
    }

    public Token get_id_estructura(String idcompuesta, String ambito_actual) {
        Token enviar = null;
        String[] ids = idcompuesta.split("\\.");

        enviar = get_id_ambitos(ids[0], ambito_actual, "Principal");
        for (int i = 1; i < ids.length; i++) {
            enviar = get_id_ambitos(ids[i], "registro", enviar.getTipo().getName());
        }

        return enviar;
    }

    public String get_id_segun_ambito_hijos(String ambito) {
        TablaSimbolos t = getTabla("Principal");
        for (int j = 0; j < t.get_ids().size(); j++) {
            if (t.get_ids().get(j).getHijos().equals(ambito)) {
                return t.get_ids().get(j).getId();
            }

        }

        return "";
    }

    ////////////////////////////////////////////////////////////////METODOS PARA CODIGO INTERMEDIO////////////////////////////////////////////////
    public int get_offset_type(String identificador) {
        String partes[] = identificador.split("\\.");
        TablaSimbolos ty = getTabla(getTabla("Principal").get_id(partes[0]).getTipo().toString());
        int offset=0;
        for (int j = 1; j < partes.length-1; j++) {
                offset+= ty.get_id(partes[j]).getOffset();
               ty = getTabla(ty.get_id(partes[j]).getTipo().toString());
            
        }
        return ty.get_id(partes[partes.length-1]).getOffset()+offset;

    }
    
     ////////////////////////////////////////////////////////////////METODOS PARA CODIGO FINAL////////////////////////////////////////////////
    public ArrayList<Token> get_variables_globales() {
        ArrayList<Token> listavars = new ArrayList();
        TablaSimbolos tp = getTabla("Principal");
        for (int j = 0; j < tp.get_ids().size(); j++) {
                if(tp.get_ids().get(j).getAmbito().equals("1") ){
                    listavars.add(tp.get_ids().get(j));
                }
            
        }
        return listavars;

    }
    
    public String get_ambit_hijos_func(String namefunc){
      TablaSimbolos tp = getTabla("Principal");  
      String ambitohijos = "";
      for (int j = 0; j < tp.get_ids().size(); j++) {
                if(tp.get_ids().get(j).getId().equals(namefunc)){
                    ambitohijos = tp.get_ids().get(j).getHijos();
                    break;
                }
            
        }
      
      return ambitohijos;
    }
    
    public Token get_offsert_var_locales(String namefunc, String var) {
        Token vartoken = null;
        TablaSimbolos tp = getTabla("Principal");
        String ambitohijos = get_ambit_hijos_func(namefunc);
        for (int j = 0; j < tp.get_ids().size(); j++) {
                if(tp.get_ids().get(j).getAmbito().equals(ambitohijos) && tp.get_ids().get(j).getId().equals(var)){
                    vartoken = tp.get_ids().get(j);
                    break;
                }
        }  
        return vartoken;

    }
    public ArrayList<Token> get_variables_locales(String namefunc) {
        
        ArrayList<Token> listavars = new ArrayList();
        TablaSimbolos tp = getTabla("Principal");
        String ambitohijos = get_ambit_hijos_func(namefunc);
        for (int j = 0; j < tp.get_ids().size(); j++) {
                if(tp.get_ids().get(j).getAmbito().equals(ambitohijos)){
                    listavars.add(tp.get_ids().get(j));
                }
            
        }
        return listavars;

    }
    

}
