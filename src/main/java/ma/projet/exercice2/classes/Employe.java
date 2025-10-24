package ma.projet.exercice2.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String telephone;

    @OneToMany(mappedBy = "chef")
    private List<Projet> projetsChef;

    @OneToMany(mappedBy = "employe")
    private List<EmployeTache> employeTaches;

    public Employe() {
        this.projetsChef = new ArrayList<>();
        this.employeTaches = new ArrayList<>();
    }

    public Employe(String nom, String prenom, String telephone) {
        this();
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public List<Projet> getProjetsChef() {
        return projetsChef;
    }

    public void setProjetsChef(List<Projet> projetsChef) {
        this.projetsChef = projetsChef;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }

    public void ajouterProjet(Projet projet) {
        if (projetsChef == null) {
            projetsChef = new ArrayList<>();
        }
        projetsChef.add(projet);
        projet.setChef(this);
    }

    public void retirerProjet(Projet projet) {
        if (projetsChef != null) {
            projetsChef.remove(projet);
            projet.setChef(null);
        }
    }

    public void ajouterTache(EmployeTache employeTache) {
        if (employeTaches == null) {
            employeTaches = new ArrayList<>();
        }
        employeTaches.add(employeTache);
        employeTache.setEmploye(this);
    }

    public void retirerTache(EmployeTache employeTache) {
        if (employeTaches != null) {
            employeTaches.remove(employeTache);
            employeTache.setEmploye(null);
        }
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public boolean estChefDeProjet() {
        return projetsChef != null && !projetsChef.isEmpty();
    }

    public int getNombreProjets() {
        return projetsChef != null ? projetsChef.size() : 0;
    }

    public int getNombreTaches() {
        return employeTaches != null ? employeTaches.size() : 0;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employe employe = (Employe) obj;
        return id == employe.id;
    }
}
