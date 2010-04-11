package org.bds;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.bds.model.User;
import org.bds.model.Item;
import org.bds.model.Tag;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Apr 11, 2010
 * Time: 6:27:55 PM
 */
public class DatabaseCleaner {
    private Session session;

    private Class<?>[] entities = new Class[]{
            User.class,
            Item.class,
            Tag.class
    };

    public DatabaseCleaner(Session session) {
        this.session = session;
    }

    public void clean() {
        Transaction tx = session.beginTransaction();
        for (Class<?> entity : entities) {
            session.createQuery("delete " + entity.getSimpleName()).executeUpdate();
        }
        tx.commit();

    }
}
