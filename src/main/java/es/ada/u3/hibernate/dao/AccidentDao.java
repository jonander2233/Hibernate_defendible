package es.ada.u3.hibernate.dao;

import es.ada.u3.hibernate.entities.Accident;
import es.ada.u3.hibernate.entities.Person;
import es.ada.u3.hibernate.utils.HibernateSessionFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AccidentDao {
    private static AccidentDao instance = new AccidentDao();
    private AccidentDao(){

    }
    public static AccidentDao getInstance(){
        return instance;
    }

    public Accident addAccident(Accident accident){
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.persist(accident);
        tx.commit();
        return accident;
    }
    public List<Accident> loadAccidents()throws HibernateException {
        Session session = HibernateSessionFactory.getSessionSingleton();
        session.clear();
        TypedQuery<Accident> query = session.createNativeQuery("select * FROM accident_ja24", Accident.class);
        return query.getResultList();
    }
    public Accident getAccidentById(String id)throws HibernateException {
        Session session = HibernateSessionFactory.getSessionSingleton();
        Accident accident = (Accident) session.get(Accident.class, id);
        if(accident != null)
            session.refresh(accident);
        return accident;
    }
    public void deleteAccident(Accident accident)throws HibernateException{
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.remove(accident);
        tx.commit();
    }
    public void deleteAccidentManual(Accident accident)throws HibernateException{
        Session session = HibernateSessionFactory.getSessionSingleton();
        String hqlDeleteParticipated = "DELETE FROM Participated p WHERE p.accident.reportNumber = :reportNumber";
        Transaction tx;
        tx = session.beginTransaction();
        session.createNativeQuery("DELETE FROM participated_ja24 WHERE report_number = :reportNumber")
                .setParameter("reportNumber", accident.getReport_number())
                .executeUpdate();
        session.remove(accident);
        tx.commit();
    }
    public void updateAccident(Accident accident)throws HibernateException{
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx;
        tx = session.beginTransaction();
        if(session.get(Accident.class, accident.getReport_number()) == null)
            throw new HibernateException("No existe el accidente");
        else{
            session.merge(accident);
        }
        tx.commit();
    }

}
