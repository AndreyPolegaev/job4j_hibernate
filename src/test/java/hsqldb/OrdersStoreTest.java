package hsqldb;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {

    private static BasicDataSource pool = new BasicDataSource();

    /** перед началом каждого теста */
    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(10);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    /** после каждого теста */
    @After
    public void clear() throws SQLException {
        PreparedStatement ps = pool.getConnection().prepareStatement("drop table orders");
        ps.execute();
        ps.close();
    }

    /** после всех тестов */
    @AfterClass
    public static void closeConnection() throws SQLException {
        pool.close();
    }

    @Test
    public void whenSave() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("new Order", "desc");
        store.save(order);
        Collection<Order> orders = store.findAll();
        assertEquals(orders.iterator().next(), order);
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("name1", "description1");
        store.save(order);
        Order rsl = store.findByName("name1");
        assertEquals(order, rsl);
    }

    @Test
    public void whenFindByID() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("name1", "description1");
        store.save(order);
        Order o1 = store.findByName("name1");
        Order rsl = store.findById(o1.getId());
        assertEquals(order, rsl);
    }

    @Test
    public void whenUpdate() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("name1", "description1");
        store.save(order);
        Order o1 = store.findByName("name1");
        o1.setName("changedOrder");
        o1.setDescription("new desc");
        store.updateOrder(o1);
        Order rsl = store.findByName(o1.getName());
        assertEquals(o1, rsl);
    }
}
