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
                    add();
                    break;
            }
        }while (!exit);
    }

    private static void add() {
        boolean back = false;
        do {
            int selection = menu.show("ADD MENU", new String[]{"Add Driver","Add Car (Driver Needed)","Add Policy(Car Needed)","Add Accident (Car/s Needed)"},"Exit");
            switch (selection){
                case 0:
                    back = true;
                case 1:
                    addDriver();
                    break;
                case 2:
                    addCar();
                    break;
                case 3:
                    addPolicy();
                    break;
                case 4:
                    addAccident();
                    break;
            }
        }while (!back);

    }

    private static void addAccident() {
    }

    private static void addPolicy() {
    }

    private static void addCar() {
    }

    private static void addDriver() {

    }

    private static void waitKey(){
        eys.imprimirYLeer("Press Enter to continue",0,0);
    }
}