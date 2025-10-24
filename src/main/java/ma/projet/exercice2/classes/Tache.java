package ma.projet.exercice2.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedQuery(
        name = "Tache.findPrixSup1000",
        query = "from Tache t where t.prix > 1000"
)
@Entity
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    private double prix;

    @ManyToOne
    @JoinColumn(name="projet_id")
    private Projet projet;

    @OneToMany(mappedBy="tache")
    private List<EmployeTache> employeTaches;
    public Tache() {
        this.employeTaches = new ArrayList<>();
    }

    public Tache(String nom, Date dateDebut, Date dateFin, double prix) {
        this();
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }

    public void ajouterEmploye(EmployeTache employeTache) {
        if (employeTaches == null) {
            employeTaches = new ArrayList<>();
        }
        employeTaches.add(employeTache);
        employeTache.setTache(this);
    }

    public void retirerEmploye(EmployeTache employeTache) {
        if (employeTaches != null) {
            employeTaches.remove(employeTache);
            employeTache.setTache(null);
        }
    }

    public boolean estEnCours() {
        Date aujourdhui = new Date();
        return dateDebut != null && dateFin != null &&
                aujourdhui.after(dateDebut) && aujourdhui.before(dateFin);
    }

    public boolean estTerminee() {
        Date aujourdhui = new Date();
        return dateFin != null && aujourdhui.after(dateFin);
    }

    public boolean estAVenir() {
        Date aujourdhui = new Date();
        return dateDebut != null && aujourdhui.before(dateDebut);
    }

    public long getDureeEnJours() {
        if (dateDebut != null && dateFin != null) {
            return (dateFin.getTime() - dateDebut.getTime()) / (1000 * 60 * 60 * 24);
        }
        return 0;
    }

    public int getNombreEmployes() {
        return employeTaches != null ? employeTaches.size() : 0;
    }

    public boolean estChere() {
        return prix > 1000;
    }

    public boolean estBonMarche() {
        return prix <= 500;
    }

    public boolean estMoyenne() {
        return prix > 500 && prix <= 1000;
    }

    public boolean estValide() {
        return nom != null && !nom.trim().isEmpty() &&
                dateDebut != null && dateFin != null &&
                dateFin.after(dateDebut) && prix >= 0;
    }

    public boolean estVide() {
        return employeTaches == null || employeTaches.isEmpty();
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tache tache = (Tache) obj;
        return id == tache.id;
    }
}
