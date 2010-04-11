package org.bds.service;

import org.bds.model.Item;
import org.bds.model.Language;
import org.bds.model.Tag;
import org.bds.util.Transactor;
import org.bds.util.UnitOfWork;
import org.bds.PersistenceTestBase;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Dec 31, 2009
 * Time: 3:31:24 PM
 */
public class PersistenceServiceTest extends PersistenceTestBase {
    @Test
    public void testHibernate() throws Exception {
        // First unit of work
        new Transactor().perform(new UnitOfWork() {
            @Override
            public void work(Session session) throws Exception {
                Item item = new Item("hello", Language.EN, "hola", Language.ES);
                session.save(item);
            }
        });

        // append _new
        new Transactor().perform(new UnitOfWork() {
            @Override
            public void work(Session session) throws Exception {
                List<Item> itemList = session.createQuery("select m from Item m order by m.text asc").list();

                assertEquals(1, itemList.size());
                Item loadedItem = itemList.get(0);
                assertEquals("hello->hola", itemList.get(0).toString());

                loadedItem.setText(loadedItem.getText() + "_new");
                loadedItem.setTranslation(loadedItem.getTranslation() + "_new");
            }
        });


        // verify
        new Transactor().perform(new UnitOfWork() {
            @Override
            public void work(Session session) throws Exception {
                List itemList = session.createQuery("select m from Item m order by m.text asc").list();
                assertEquals(1, itemList.size());
                assertEquals("hello_new->hola_new", itemList.get(0).toString());
            }
        });
    }

    @Test
    public void testTags() throws Exception {

        new Transactor().perform(new UnitOfWork() {
            @Override
            public void work(Session session) throws Exception {
                Item item = new Item("hello", Language.EN, "hola", Language.ES);
                item.addTag(new Tag("leccion 1"));
                item.addTag(new Tag("greetings"));
                session.save(item);
            }
        });

        new Transactor().perform(new UnitOfWork() {
            @Override
            public void work(Session session) throws Exception {
                List<Item> itemList = session.createQuery("select m from Item m order by m.text asc").list();

                assertEquals(1, itemList.size());
                List<Tag> tags = (List<Tag>) session.createQuery("select item.tags from Item item").list();
                Set<String> tagNameSet = new HashSet<String>();
                for (Tag t : tags) {
                    tagNameSet.add(t.toString());
                }
                assertEquals(2, tagNameSet.size());
                assertTrue(tagNameSet.contains("[tag:leccion 1]"));
                assertTrue(tagNameSet.contains("[tag:greetings]"));
            }
        });
    }
}
