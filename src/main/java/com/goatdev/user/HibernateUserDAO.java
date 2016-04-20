package com.goatdev.user;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Hiberante implementation of User data access object.
 * Created by ran on 4/19/16.
 */
@Component
public class HibernateUserDAO implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean saveUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        boolean success = true;

        try {
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            success = false;
        } finally {
            session.close();
        }

        return true;
    }

    @Override
    public User getUser(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM User WHERE username = :username";
        Query query = session.createQuery(hql);
        query.setParameter("username", username);
        User result = (User)query.uniqueResult();
        transaction.commit();
        session.close();

        return result;
    }

}
