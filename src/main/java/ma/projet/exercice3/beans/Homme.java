package ma.projet.exercice3.beans;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
        this.mariages = new ArrayList<>();
    }

    public Homme(String nom, String prenom, LocalDate dateNaissance) {
        this.mariages = new ArrayList<>();
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

    public void ajouterMariage(Mariage mariage) {
        if (mariages == null) {
            mariages = new ArrayList<>();
        }
        mariages.add(mariage);
        mariage.setHomme(this);
    }

    public void retirerMariage(Mariage mariage) {
        if (mariages != null) {
            mariages.remove(mariage);
            mariage.setHomme(null);
        }
    }

    public boolean estMarie() {
        return mariages != null && !mariages.isEmpty();
    }

    public boolean estDivorce() {
        if (mariages == null || mariages.isEmpty()) {
            return false;
        }
        return mariages.stream().anyMatch(m -> m.getDateFin() != null);
    }

    public int getNombreMariages() {
        return mariages != null ? mariages.size() : 0;
    }

    public int getNombreEnfants() {
        if (mariages == null || mariages.isEmpty()) {
            return 0;
        }
        return mariages.stream()
                .mapToInt(Mariage::getNbrEnfant)
                .sum();
    }

    public Mariage getMariageActuel() {
        if (mariages == null || mariages.isEmpty()) {
            return null;
        }
        return mariages.stream()
                .filter(m -> m.getDateFin() == null)
                .findFirst()
                .orElse(null);
    }

    public boolean aEteMarieDeuxFois() {
        return getNombreMariages() >= 2;
    }



    public boolean peutSeMarier() {
        return getMariageActuel() == null;
    }

    public int getAge() {
        if (getDateNaissance() == null) {
            return 0;
        }
        return LocalDate.now().getYear() - getDateNaissance().getYear();
    }

    public boolean estMajeur() {
        return getAge() >= 18;
    }

    @Override
    public String toString() {
        return "Homme{" +
                "id=" + id +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", nombreMariages=" + getNombreMariages() +
                ", age=" + getAge() +
                '}';
    }

}
