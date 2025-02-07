package es.ada.u3.hibernate.dao;

import es.ada.u3.hibernate.entities.Car;
import es.ada.u3.hibernate.entities.Person;
import es.ada.u3.hibernate.utils.HibernateSessionFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {
    private static CarDao instance = new CarDao();
    private CarDao(){

    }
    public static CarDao getInstance(){
        return instance;
    }
    public Car addCar(Car car){
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.persist(car);
        tx.commit();
        return car;
    }
    public List<Car> loadCars() throws HibernateException {
        Session session = HibernateSessionFactory.getSessionSingleton();
        session.clear();
        TypedQuery<Car> query = session.createNativeQuery("SELECT * FROM car_ja24", Car.class);
        return query.getResultList();
    }

    public Car getCarById(String id)throws HibernateException {
        Session session = HibernateSessionFactory.getSessionSingleton();
        Car car =(Car) session.get(Car.class, id);
        if (car != null)
            session.refresh(car);
        return car;
    }
    public void deleteCar(Car car)throws HibernateException{
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx = null;
        tx = session.beginTransaction();

        Car car1 = session.get(Car.class, car.getLicense_id());
        if (car != null) {
            car.getAccidents().clear();
            session.delete(car);
        }

        tx.commit();
        session.close();
    }
}
