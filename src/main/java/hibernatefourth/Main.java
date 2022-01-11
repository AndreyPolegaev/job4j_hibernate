package hibernatefourth;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            /** создать кадидатов */
            Candidate ivan = new Candidate("Ivan", "Python programmer", 3000);
            Candidate andrey = new Candidate("Andrey", "Java programmer", 3200);
            Candidate petr = new Candidate("Petr", "Angular programmer", 3300);
            session.save(ivan);
            session.save(andrey);
            session.save(petr);

            /** получить всех кандидатов */
            List<Candidate> ivan1 = session.createQuery("from Candidate").list();

            /** получить кандидата по id */
            Query query = session.createQuery("from Candidate c where c.id = :param");
            Candidate andrey1 = (Candidate) query.setParameter("param", 1).uniqueResult();

            /** получить кандидата по имени */
            Query query1 = session.createQuery("from Candidate c where c.name = :param");
            Candidate petr1 = (Candidate) query.setParameter("param", "Petr").uniqueResult();

            /** обновление записи */
            session.createQuery("update Candidate c set c.name = :param1, c.salary = :param2, c.experience = :param3")
                    .setParameter("param1", "newName")
                    .setParameter("param2", 4000)
                    .setParameter("param3", "new Experiense").executeUpdate();

            Query query2 = session.createQuery("delete from Candidate where id = :param");
            query2.setParameter("param", 1).executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
