package org.bds.util;

import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Apr 11, 2010
 * Time: 6:22:37 PM
 */
public interface UnitOfWork {
    void work(Session session) throws Exception;
}
