package es.ada.u3.hibernate;

import org.jonander2233.Lang;
import org.jonander2233.lib_personal.Eys;
import org.jonander2233.lib_personal.Menu;

public class Main {
    private static Menu menu = new Menu(Lang.EN);
    private static Eys eys = new Eys(Lang.EN);
    public static void main(String[] args) {
        menu.show("CARS MENU", new String[]{"ADD",""},"Exit");





    }

}