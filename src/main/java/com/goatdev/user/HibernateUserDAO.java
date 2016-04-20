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
    public Integer createUser(User user) {
        Integer id = null;

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            id = (Integer)session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            id = null;
            transaction.rollback();
        }
        session.close();
        return id;
    }

    @Override
    public boolean updateUser(User user) {
        boolean success = true;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            success = false;
            transaction.rollback();
        }

        session.close();
        return success;
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
