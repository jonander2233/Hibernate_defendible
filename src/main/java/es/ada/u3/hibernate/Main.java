package es.ada.u3.hibernate;

import org.jonander2233.Lang;
import org.jonander2233.lib_personal.Eys;
import org.jonander2233.lib_personal.Menu;

public class Main {
    private static Menu menu = new Menu(Lang.EN);
    private static Eys eys = new Eys(Lang.EN);
    public static void main(String[] args) {
        boolean exit = false;
        do {
            int selection = menu.show("CARS MENU", new String[]{"ADD",""},"Exit");
            switch (selection){
                case 0:
                    exit = true;
                case 1:
                    break;
            }
        }while (!exit);




    }

}