package es.ada.u3.hibernate.dao;

import es.ada.u3.hibernate.entities.Person;
import es.ada.u3.hibernate.entities.Policy;
import es.ada.u3.hibernate.utils.HibernateSessionFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PolicyDao {
    private static PolicyDao instance = new PolicyDao();
    private PolicyDao(){
    }
    public static PolicyDao getInstance(){
        return instance;
    }

    public Policy addPolicy(Policy policy){
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.persist(policy);
        tx.commit();
        return policy;
    }
    public List<Policy> loadPolicies()throws HibernateException {
        Session session = HibernateSessionFactory.getSessionSingleton();
        TypedQuery<Policy> query = session.createNativeQuery("select * FROM policy_ja24", Policy.class);
        session.clear();
        return query.getResultList();
    }
    public Policy getPolicyById(String id)throws HibernateException {
        Session session = HibernateSessionFactory.getSessionSingleton();
        Policy policy =(Policy) session.get(Policy.class, id);
        if(policy != null)
            session.refresh(policy);
        return policy;
    }
    public void deletePolicy(Policy policy)throws HibernateException{
        Session session = HibernateSessionFactory.getSessionSingleton();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.remove(policy);
        tx.commit();
    }

}
