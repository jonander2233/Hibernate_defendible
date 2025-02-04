package es.ada.u3.hibernate.dao;

import es.ada.u3.hibernate.entities.Person;
import es.ada.u3.hibernate.utils.HibernateSessionFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonDao {
    private static PersonDao instance = new PersonDao();
    private PersonDao(){

    }
    public static PersonDao getInstance(){
        return instance;
    }

    public Person addPerson(Person person){
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.persist(person);
        tx.commit();
        return person;
    }
    public List<Person> loadPersons()throws HibernateException {
        Session session = HibernateSessionFactory.getSessionSingleton();
        TypedQuery<Person> query = session.createNativeQuery("select * FROM Person", Person.class);
        return query.getResultList();
    }

}
