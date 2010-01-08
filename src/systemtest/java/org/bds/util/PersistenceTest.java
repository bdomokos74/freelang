package org.bds.util;

import org.bds.model.Item;
import org.bds.model.Language;
import org.bds.model.Tag;
import org.bds.model.User;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Dec 31, 2009
 * Time: 3:31:24 PM
 */
public class PersistenceTest {
    @Before
    public void beforeTest() {
    }

    @After
    public void afterTest() {
        // We need to clean the database after each testcase, if we want to make the testcases independent.
        // When a new entity is added, the delete needs to be added here as well.
        Session em = HibernateHelper.INSTANCE.createSession();
        Transaction tx = em.getTransaction();
        tx.begin();
        deleteAll(em, Item.class);
        deleteAll(em, User.class);
        tx.commit();
        em.close();
    }

    private <T> void deleteAll(Session session, Class<T> clazz) {
        @SuppressWarnings({"unchecked", "JpaQlInspection"})
        List<T> list = (List<T>) session
                .createQuery("select m from " + clazz.getSimpleName() + " m")
                .list();
        for (T t : list) {
            session.delete(t);
        }
    }

    @AfterClass
    public static void shutdown() {
        HibernateHelper.INSTANCE.shutdown();
    }

    @Test
    public void testFirst() {
        assertEquals("Item", Item.class.getSimpleName());
    }

    @Test
    public void testHibernate() {
        // First unit of work
        Session em = HibernateHelper.INSTANCE.createSession();
        Transaction tx = em.getTransaction();

        tx.begin();
        Item item = new Item("hello", Language.EN, "hola", Language.ES);
        em.persist(item);
        tx.commit();
        em.close();

        // append _new
        Session newEm = HibernateHelper.INSTANCE.createSession();
        Transaction newTx = newEm.getTransaction();
        newTx.begin();
        List<Item> itemList = newEm
                .createQuery("select m from Item m order by m.text asc")
                .list();

        assertEquals(1, itemList.size());
        Item loadedItem = itemList.get(0);
        assertEquals("hello->hola", itemList.get(0).toString());

        loadedItem.setText(loadedItem.getText() + "_new");
        loadedItem.setTranslation(loadedItem.getTranslation() + "_new");
        newTx.commit();
        newEm.close();

        // verify
        newEm = HibernateHelper.INSTANCE.createSession();
        newTx = newEm.getTransaction();
        newTx.begin();

        itemList = newEm
                .createQuery("select m from Item m order by m.text asc")
                .list();

        assertEquals(1, itemList.size());
        assertEquals("hello_new->hola_new", itemList.get(0).toString());
        newTx.commit();
        newEm.close();

    }

    @Test
    public void testTags() throws Exception {
        Session em = HibernateHelper.INSTANCE.createSession();
        Transaction tx = em.getTransaction();
        tx.begin();

        Item item = new Item("hello", Language.EN, "hola", Language.ES);
        item.addTag(new Tag("leccion 1"));
        item.addTag(new Tag("greetings"));
        em.persist(item);

        tx.commit();
        em.close();

        em = HibernateHelper.INSTANCE.createSession();
        tx = em.getTransaction();
        tx.begin();
        List<Item> itemList = em
                .createQuery("select m from Item m order by m.text asc")
                .list();

        assertEquals(1, itemList.size());
        List<Tag> tags = (List<Tag>)em.createQuery("select item.tags from Item item").list();
        Set<String> tagNameSet = new HashSet<String>();
        for (Tag t : tags) {
            tagNameSet.add(t.toString());
        }
        assertEquals(2, tagNameSet.size());
        assertTrue(tagNameSet.contains("[tag:leccion 1]"));
        assertTrue(tagNameSet.contains("[tag:greetings]"));
        tx.commit();
        em.close();
    }
}
