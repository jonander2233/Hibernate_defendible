package es.ada.u3.hibernate;

import com.mysql.cj.util.StringUtils;
import es.ada.u3.hibernate.dao.AccidentDao;
import es.ada.u3.hibernate.dao.CarDao;
import es.ada.u3.hibernate.dao.PersonDao;
import es.ada.u3.hibernate.dao.PolicyDao;
import es.ada.u3.hibernate.entities.Accident;
import es.ada.u3.hibernate.entities.Car;
import es.ada.u3.hibernate.entities.Person;
import es.ada.u3.hibernate.entities.Policy;
import jakarta.persistence.EntityExistsException;
import org.hibernate.TransientObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.jonander2233.Lang;
import org.jonander2233.lib_personal.Eys;
import org.jonander2233.lib_personal.Menu;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.Date;

public class Main {
    private static final int MIN_YEAR = 1700;
    private static Menu menu = new Menu(Lang.EN);
    private static Eys eys = new Eys(Lang.EN);
    private static final  int STRING_MAX = 50;
    private static PersonDao personDao = PersonDao.getInstance();
    private static CarDao carDao = CarDao.getInstance();
    private static PolicyDao policyDao = PolicyDao.getInstance();
    private static AccidentDao accidentDao = AccidentDao.getInstance();
    public static void main(String[] args) {
        boolean exit = false;
        do {
            int selection = menu.show("CARS MENU", new String[]{"ADD","LIST","DELETE","FILTER"},"Exit");
            switch (selection){
                case 0:
                    exit = true;
                    break;
                case 1:
                    add();
                    break;
                case 2:
                    listObjects();
                    break;
                case 3:
                    deleteObjects();
                    break;
                case 4:
                    break;
            }
        }while (!exit);
    }

    private static void deleteObjects() {
        boolean back = false;
        do {
            int selection = menu.show("DELETE MENU", new String[]{"Delete Driver","Delete Car","Delete Policy","Delete Accident"},"Back");
            switch (selection){
                case 0:
                    back = true;
                    break;
                case 1:
                    deleteDriver();
                    break;
                case 2:
                    deleteCar();
                    break;
                case 3:
                    deletePolicy();
                    break;
                case 4:
                    deleteAccident();
                    break;
            }
        }while (!back);
    }

    private static void deleteAccident() {
        boolean back = false;
        do{
            String accidentID = eys.imprimirYLeer("Insert accident ID to delete or press enter to exit",0,STRING_MAX);
            if (!accidentID.isEmpty()){
                Accident accident = accidentDao.getAccidentById(accidentID);
                if(accident == null){
                    System.out.println("The Accident with id " + accidentID + " doesn't exist");
                    waitKey();
                } else {
                    
                    boolean delete = eys.ImprimirYleerCharSN("Are you sure you want to delete the accident:" + accidentID + " " + accident.getLocation() + "?");
                    if(delete){
                        try {
                            accidentDao.deleteAccident(accident);
                            System.out.println("Accident deleted successfully");
                            waitKey();
                        }catch (IllegalStateException e){
                            System.out.println("Error");
                            waitKey();
                        }
                    }
                }
            } else {
                back = true;
            }
        }while (!back);

    }

    private static void deletePolicy() {
        boolean back = false;
        do{
            String policyID = eys.imprimirYLeer("Insert policy ID to delete or press enter to exit",0,STRING_MAX);
            if (!policyID.isEmpty()){
                Policy policy = policyDao.getPolicyById(policyID);
                if(policy == null){
                    System.out.println("The Policy with id " + policyID + " doesn't exist");
                    waitKey();
                } else {
                    boolean delete = eys.ImprimirYleerCharSN("Are you sure you want to delete the policy:" + policyID + " " + policy.getCar().getModel() + "?");
                    if(delete){
                        try {
                            policyDao.deletePolicy(policy);
                            System.out.println("Policy deleted successfully");
                            waitKey();
                        }catch (IllegalStateException e){
                            System.out.println("Error");
                            waitKey();
                        }
                    }
                }
            } else {
                back = true;
            }
        }while (!back);
    }

    private static void deleteCar() {
        boolean back = false;
        do{
            String licenseID = eys.imprimirYLeer("Insert car license ID to delete or press enter to exit",0,STRING_MAX);
            if (!licenseID.isEmpty()){
                Car car = carDao.getCarById(licenseID);
                if(car == null){
                    System.out.println("The Car with id " + licenseID + " doesn't exist");
                    waitKey();
                } else {
                    if (car.getPolicy() != null){
                        System.out.println("The car has a policy associated, please delete the policy first");
                        waitKey();
                    } else {
                        boolean delete = eys.ImprimirYleerCharSN("Are you sure you want to delete the car:" + licenseID + " " + car.getModel() + "?");
                        if(delete){
                            try {
                                carDao.deleteCar(car);
                                System.out.println("Car deleted successfully");
                                waitKey();
                            }catch (IllegalStateException e){
                                System.out.println("Error");
                                waitKey();
                            }
                        }
                    }
                }
            } else {
                back = true;
            }
        }while (!back);
    }

    private static void deleteDriver() {
        boolean back = false;
        do{
            String driverID = eys.imprimirYLeer("Insert driver ID to delete or press enter to exit",0,STRING_MAX);
            if (!driverID.isEmpty()){
                Person driver = personDao.getPersonById(driverID);

                if(driver == null){
                    System.out.println("The Driver with id " + driverID + " doesn't exist");
                    waitKey();
                } else {
                    boolean hasPolicy = false;
                    for (Car car : driver.getCars()) {
                        if(car.getPolicy() != null){
                            hasPolicy = true;
                            break;
                        }
                    }
                    if(hasPolicy){
                        System.out.println("The driver has cars with policies associated, please delete the policies first");
                        waitKey();
                    }else {
                        boolean delete = eys.ImprimirYleerCharSN("Are you sure you want to delete the driver:" + driverID + " " + driver.getName() + "?");
                        if (delete) {
                            personDao.deletePerson(driver);
                            System.out.println("Driver deleted successfully");
                            waitKey();
                        }
                    }
                }
            }else {
                back = true;
            }
        }while (!back);
    }

    private static void listObjects() {
        boolean back = false;
        do {
            int selection = menu.show("LIST MENU", new String[]{"List Drivers","List Cars","List Policies","List Accidents"},"Back");
            switch (selection){
                case 0:
                    back = true;
                    break;
                case 1:
                    listDrivers();
                    break;
                case 2:
                    listCars();
                    break;
                case 3:
                    listPolicies();
                    break;
                case 4:
                    listAccidents();
                    break;
            }
        }while (!back);
    }

    private static void listAccidents() {
        System.out.println("Accidents:");
        for (Accident accident : accidentDao.loadAccidents()) {
            System.out.println(accident);
        }
        waitKey();
    }

    private static void listPolicies() {
        System.out.println("Policies:");
        for (Policy policy : policyDao.loadPolicies()) {
            System.out.println(policy);
        }
        waitKey();
    }

    private static void listCars() {
        System.out.println("Cars:");
        for (Car car : carDao.loadCars()) {
            System.out.println(car);
        }
        waitKey();
    }

    private static void listDrivers() {
        System.out.println("Drivers:");
        for (Person person : personDao.loadPersons()) {
            System.out.println(person.toString());
        }
        waitKey();
    }

    private static void add() {
        boolean back = false;
        do {
            int selection = menu.show("ADD MENU", new String[]{"Add Driver","Add Car (Driver Needed)","Add Policy(Car Needed)","Add Accident","Add Car/s to accident"},"Back");
            switch (selection){
                case 0:
                    back = true;
                    break;
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
                case 5:
                    addCarToAccident();
                    break;
            }
        }while (!back);
    }

    private static void addCarToAccident() {
        boolean back = false;
        do {
            String accidentID = eys.imprimirYLeer("Insert accident ID or press enter to exit",0,STRING_MAX);
            if (!accidentID.isEmpty()){
                Accident accident = accidentDao.getAccidentById(accidentID);
                if(accident == null){
                    System.out.println("The Accident with id " + accidentID + " doesn't exist");
                    waitKey();
                } else {
                    String licenseID;
                    boolean changes = false;
                    do {
                        licenseID = eys.imprimirYLeer("Insert Car license ID or press enter to finish",0,STRING_MAX);
                        if(licenseID.isEmpty()){
                            if (changes){
                                accidentDao.updateAccident(accident);
                                System.out.println("Accident updated successfully in database");
                            }
                            break;
                        }
                        Car car = carDao.getCarById(licenseID);
                        if(car == null){
                            System.out.println("The Car with id " + licenseID + " doesn't exist");
                            waitKey();
                        }else {
                            if (accident.getCarsInvolved().contains(car)){
                                System.out.println("The car is already in the accident");
                                waitKey();
                            } else {
                                boolean insert = eys.ImprimirYleerCharSN("Are you sure you want to insert the car to the accident?:" + "Accident id=" + accidentID + ", Car licence id=" + licenseID + "?");
                                if(insert){
                                    accident.getCarsInvolved().add(car);
                                    System.out.println("Car added to accident successfully");
                                    changes = true;
                                }
                            }
                        }
                    }while (true);

                }
            }else{
                back = true;
            }
        }while (!back);
    }

    private static void addAccident() {
        boolean back = false;
        do {
            String licenseID = eys.imprimirYLeer("Insert Report number or press enter to exit",0,STRING_MAX);
            if (!licenseID.isEmpty()){
                String accidentID = eys.imprimirYLeer("Insert the accident report number",1,STRING_MAX);
                if(accidentDao.getAccidentById(accidentID) != null){
                    System.out.println("The accident with id " + accidentID + " already exists");
                    waitKey();
                }else {
                    String location = eys.imprimirYLeer("Insert the location of the accident",1,STRING_MAX);
                    boolean insert = eys.ImprimirYleerCharSN("Are you sure you want to insert the accident?:" +  "Accident id="+accidentID +  "?");
                    if(insert){
                        accidentDao.addAccident(new Accident(accidentID,location));
                        System.out.println("Accident added successfully");
                    }
                }
            }else{
                back = true;
            }
        }while (!back);
    }

    private static void addPolicy() {
        boolean back = false;
        do {
            String licenseID = eys.imprimirYLeer("Insert Car license ID or press enter to exit",0,STRING_MAX);
            if (!licenseID.isEmpty()){
                Car car = carDao.getCarById(licenseID);
                if(car == null){
                    System.out.println("The Car with id " + licenseID + " doesn't exist");
                    waitKey();
                } else {
                    if(car.getPolicy() != null){
                        System.out.println("The car already has a policy");
                        waitKey();
                    }else {
                        String policyID = eys.imprimirYLeer("Insert the policy id", 1, STRING_MAX);
                        if (policyDao.getPolicyById(policyID) == null) {
                            boolean insert = eys.ImprimirYleerCharSN("Are you sure you want to insert the policy?:" + "Policy id=" + policyID + ", Car licence id=" + car.getLicense_id() + "?");
                            if (insert) {
                                policyDao.addPolicy(new Policy(policyID, car));
                                System.out.println("Policy added successfully");
                            }
                        } else {
                            System.out.println("Policy id already exists");
                            waitKey();
                        }
                    }
                }
            }else{
                back = true;
            }
        }while (!back);
    }


    private static void addCar() {
        boolean back = false;
        do {
            String driverID = eys.imprimirYLeer("Insert driver ID or press enter to exit",0,STRING_MAX);
            if (!driverID.isEmpty()){
                Person driver = personDao.getPersonById(driverID);
                if(driver == null){
                    System.out.println("The Driver with id " + driverID + " doesn't exist");
                    waitKey();
                } else {
                    String licenceID = eys.imprimirYLeer("Insert the car license id",1,STRING_MAX);
                    if(carDao.getCarById(licenceID) == null){
                        String model = eys.imprimirYLeer("Insert the model of the car",1,STRING_MAX);
                        Integer year = eys.imprimirYLeerInt("Insert the year of the car",MIN_YEAR, LocalDate.now().getYear());
                        boolean insert = eys.ImprimirYleerCharSN("Are you sure you want to insert the car?:" + model + " " + year + " " + driver.getName() + "?");
                        if(insert){
                            carDao.addCar(new Car(licenceID,model,year,driver));
                            System.out.println("Car added successfully");
                        }
                    }else {
                        System.out.println("Car license id already exists");
                        waitKey();
                    }
                }
            }else{
                back = true;
            }
        }while (!back);
    }

    private static void addDriver() {
        boolean back = false;
        do {
            String driverID = eys.imprimirYLeer("Insert driver ID or press enter to exit",0,STRING_MAX);
            if (!driverID.isEmpty()){
                Person driver = personDao.getPersonById(driverID);
                if(driver != null){
                    System.out.println("The Driver with id " + driverID + " already exists");
                    waitKey();
                } else {
                    String name = eys.imprimirYLeer("Insert Name",1,STRING_MAX);
                    String address = eys.imprimirYLeer("Insert address",1,STRING_MAX);
                    boolean insert = eys.ImprimirYleerCharSN("Are you sure you want to insert the driver:" + driverID + " " + name + " " + address + "?");
                    if (insert)
                        personDao.addPerson(new Person(driverID,address,name));
                }
            }else{
                back = true;
            }
        }while (!back);
    }

    private static void waitKey(){
        eys.imprimirYLeer("Press Enter to continue",0,0);
    }
}