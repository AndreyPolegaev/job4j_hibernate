package hibernatefourth;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class Main2 {

    public static void main(String[] args) {

        Candidate candidateRSL = null;
        Vacancybase vacancybase = null;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Vacancy java = new Vacancy("java vacancy");
            Vacancybase hhru = new Vacancybase("hh.ru");
            hhru.addVacancy(java);
            Candidate andrey = new Candidate("Andrey", "very well experience", 3000);
            andrey.setVacancybase(hhru);

            session.save(hhru);
            session.save(andrey);

            /** извлечем кандидата, его базу вакансий, и все вакансии оттуда*/

            Query<Candidate> query = session.createQuery("select distinct c from Candidate c " +
                    "join fetch c.vacancybase vb " +
                    "join fetch vb.vacancies where c.id = 1");
            candidateRSL = query.uniqueResult();

            Query<Vacancybase> query2 = session.createQuery("select distinct v from Vacancybase v " +
                    "join fetch v.vacancies vc ");
            vacancybase = query2.uniqueResult();


            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        System.out.println(candidateRSL);
        System.out.println(vacancybase);
    }
}
