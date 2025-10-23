package ma.projet.exercice3.beans;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("HOMME")
public class Homme extends Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy="homme")
    private List<Mariage> mariages;

    public Homme() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }

}
