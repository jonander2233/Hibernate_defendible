package es.ada.u3.hibernate.dao;

import es.ada.u3.hibernate.entities.Person;
import es.ada.u3.hibernate.utils.HibernateSessionFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
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
        session.clear();
        TypedQuery<Person> query = session.createNativeQuery("select * FROM person_ja24", Person.class);
        return query.getResultList();
    }
    public Person getPersonById(String id)throws HibernateException {
        Session session = HibernateSessionFactory.getSessionSingleton();
        Person person = (Person) session.get(Person.class, id);
        if(person != null)
            session.refresh(person);
        return person;
    }

    public void deletePerson(Person person)throws HibernateException{
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.remove(person);
        tx.commit();
    }
    public Person getDriverWithMostVehicles()throws HibernateException{
        Session session = HibernateSessionFactory.getSessionSingleton();
        session.clear();
        String hql = """
            SELECT p FROM Person p
            LEFT JOIN p.cars c
            GROUP BY p
            ORDER BY COUNT(c) DESC
        """;

        Person person = session.createQuery(hql, Person.class)
                       .setMaxResults(1)
                       .getSingleResult();
        return person;
    }
}
