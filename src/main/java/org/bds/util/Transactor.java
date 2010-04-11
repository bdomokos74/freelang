package org.bds.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Apr 11, 2010
 * Time: 6:22:54 PM
 */
public class Transactor {
    private final Session session;

    public Transactor() {
        this.session = HibernateHelper.INSTANCE.createSession();
    }

    public Transactor(Session session) {
        this.session = session;
    }

    public void perform(UnitOfWork unitOfWork) throws Exception {
        Transaction tx = session.beginTransaction();
        try {
            unitOfWork.work(session);
            tx.commit();
        } catch (HibernateException e) {
            LoggerFactory.getLogger(getClass()).info("HibernateException: ", e);
            throw e;
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).info("Rolling back", e);
            tx.rollback();
            throw e;
        }
        finally {
            LoggerFactory.getLogger(getClass()).info("Closing session");
            session.close();
        }
    }
}
