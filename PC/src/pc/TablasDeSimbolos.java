/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc;

import java.util.ArrayList;
import pc.Tipos.Tipo;

/**
 *
 * @author Denisse
 */
public class TablasDeSimbolos {

    ArrayList<TablaSimbolos> tablas;

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
        boolean esta = false;
        TablaSimbolos t = getTabla(nametabla);
        for (int j = 0; j < t.get_ids().size(); j++) {
            if (t.get_ids().get(j).getId().equals(id) && t.get_ids().get(j).getAmbito().equals(ambito)) {
                esta = true;
                break;
            }

        }

        return esta;
    }

    public boolean hay_main() {
        boolean esta = false;
        TablaSimbolos t = getTabla("Principal");

        for (int j = 0; j < t.get_ids().size(); j++) {
            if (t.get_ids().get(j).getId().toLowerCase().equals("main") && t.get_ids().get(j).getTipo().getName().equals("Proc")) {
                esta = true;
                break;
            }

        }

        return esta;
    }

    public boolean hay_proc_o_fun() {
        boolean esta = false;
        TablaSimbolos t = getTabla("Principal");

        for (int j = 0; j < t.get_ids().size(); j++) {
            if (t.get_ids().get(j).getTipo().getName().equals("Proc")) {
                esta = true;
                break;
            }

        }

        return esta;
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
        boolean esta = false;
        TablaSimbolos t = getTabla("Principal");
        String am = ambito;
        while (!am.equals("")) {
            for (int j = 0; j < t.get_ids().size(); j++) {
                if (t.get_ids().get(j).getId().equals(id) && t.get_ids().get(j).getAmbito().equals(am)) {
                    esta = true;
                    break;
                }

            }
            am = remover(am);

        }
        return esta;
    }

    public Token get_id_ambitos(String id, String ambito, String tabla) {
        Token enviar = null;
        TablaSimbolos t = getTabla(tabla);
        String am = ambito;
        while (!am.equals("")) {
            for (int j = 0; j < t.get_ids().size(); j++) {
                if (!ambito.equals("registro")) {
                    if (t.get_ids().get(j).getId().equals(id) && t.get_ids().get(j).getAmbito().equals(am)) {
                        enviar = t.get_ids().get(j);
                        break;
                    }
                }else{
                    if (t.get_ids().get(j).getId().equals(id) ) {
                        enviar = t.get_ids().get(j);
                        break;
                    }
                }

            }
            am = remover(am);

        }
        return enviar;
    }
  
    public Object[] existe_id_estructura(String id, String ambito) {
        boolean esta = false;
        String var_o_registro = "var";
        String name_registro = "";
        String name_id = "";
        String id_no_encontrada="";
        String name_registro_pertenece_name_id = "";
        Token id_var = null;
        String[] ids = id.split("\\.");///////// buscar si existen los nombres de variables dentro de los registros(type)
        if (existe_id_ambitos(ids[0], ambito)) {
            var_o_registro = "registro";
            id_var = get_id_ambitos(ids[0], ambito, "Principal");
            for (int i = 1; i < ids.length; i++) {
                if(i==1){
                    name_id = id_var.getId();
                    name_registro_pertenece_name_id = "--";
                    name_registro = id_var.getTipo().getName();
                }
                if (getTabla(id_var.getTipo().getName()) != null) {
                    name_registro_pertenece_name_id = id_var.getTipo().getName();
                    if (get_id_ambitos(ids[i],"registro", id_var.getTipo().getName()) != null) {
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
        
        Object[] o = {esta, var_o_registro, name_registro, name_id,id_no_encontrada,name_registro_pertenece_name_id};
        return o;
    }

}
