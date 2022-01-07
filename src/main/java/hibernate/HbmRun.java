package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Mark bmw = new Mark("BMW");
            Model m1 = new Model("1 series");
            Model m2 = new Model("2 series");
            Model m3 = new Model("3 series");
            Model m4 = new Model("4 series");
            Model m5 = new Model("5 series");

            bmw.addModel(m1);
            bmw.addModel(m2);
            bmw.addModel(m3);
            bmw.addModel(m4);
            bmw.addModel(m5);

            session.save(bmw);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
