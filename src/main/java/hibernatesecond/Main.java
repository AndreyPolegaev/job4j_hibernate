package hibernatesecond;

import hibernate.Mark;
import hibernate.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book java = new Book("Java for Beginners");
            Book sql = new Book("SQL for Beginners");

            Author blinov = new Author("Blinov");
            blinov.addBook(java);
            blinov.addBook(sql);

            Author romanchik = new Author("Romanchik");
            romanchik.addBook(java);

            session.persist(blinov);
            session.persist(romanchik);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
