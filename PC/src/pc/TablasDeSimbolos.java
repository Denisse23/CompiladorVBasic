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

    

}
