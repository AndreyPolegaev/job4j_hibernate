package hibernatefourth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vacancybase", catalog = "hiber", schema = "h4")
public class Vacancybase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    /** при удалении базы вакансий, удалить все вакансии */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vacancybase", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies = new ArrayList<>();

    /** при удалении базы вакансий, удалить всех кандидатов */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "vacancybase", cascade = CascadeType.ALL)
    private Candidate candidate;

    public Vacancybase(String name) {
        this.name = name;
    }

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
        vacancy.setVacancybase(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancybase that = (Vacancybase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vacancybase{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
