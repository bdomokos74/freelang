package org.bds;

import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.bds.util.HibernateHelper;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Apr 11, 2010
 * Time: 6:24:48 PM
 */
public abstract class PersistenceTestBase {
    @Before
    public void beforeTest() {
        Session session = HibernateHelper.INSTANCE.createSession();
        new DatabaseCleaner(session).clean();
        session.close();
    }

    @AfterClass
    public static void shutdown() {
//        HibernateHelper.INSTANCE.shutdown();
    }
}