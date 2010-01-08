package org.bds.util;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Jan 2, 2010
 * Time: 10:15:57 AM
 */
public enum HibernateHelper {
    INSTANCE,;

//    private EntityManagerFactory sessionFactory;
    private SessionFactory sessionFactory;
    private ThreadLocal<Session> currentSessionThreadLocal;

    private HibernateHelper() {

        try {
            sessionFactory = new AnnotationConfiguration().configure("/hibernate.cfg.xml").buildSessionFactory();
        } catch (HibernateException e) {
            LoggerFactory.getLogger(getClass()).error("Error during configure", e);
            throw e;
        }

        currentSessionThreadLocal = new ThreadLocal<Session>() {
            @Override
            protected Session initialValue() {
                return sessionFactory.openSession();
            }
        };
    }

    public Session getCurrentEntityManager() {
        return currentSessionThreadLocal.get();
    }

    public Session createSession() {
        return sessionFactory.openSession();
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
