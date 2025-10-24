package ma.projet.exercice2.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name="chef_id")
    private Employe chef;

    @OneToMany(mappedBy="projet")
    private List<Tache> taches;
    public Projet() {
        this.taches = new ArrayList<>();
    }

    public Projet(String nom, Date dateDebut, Date dateFin) {
        this();
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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

    public Employe getChef() {
        return chef;
    }

    public void setChef(Employe chef) {
        this.chef = chef;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public void ajouterTache(Tache tache) {
        if (taches == null) {
            taches = new ArrayList<>();
        }
        taches.add(tache);
        tache.setProjet(this);
    }

    public void retirerTache(Tache tache) {
        if (taches != null) {
            taches.remove(tache);
            tache.setProjet(null);
        }
    }

    public void assignerChef(Employe chef) {
        this.chef = chef;
        if (chef != null && !chef.getProjetsChef().contains(this)) {
            chef.getProjetsChef().add(this);
        }
    }

    public void retirerChef() {
        if (chef != null) {
            chef.getProjetsChef().remove(this);
            this.chef = null;
        }
    }

    public boolean estEnCours() {
        Date aujourdhui = new Date();
        return dateDebut != null && dateFin != null &&
                aujourdhui.after(dateDebut) && aujourdhui.before(dateFin);
    }

    public boolean estTermine() {
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

    public int getNombreTaches() {
        return taches != null ? taches.size() : 0;
    }

    public boolean aUnChef() {
        return chef != null;
    }

    public boolean estVide() {
        return taches == null || taches.isEmpty();
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Projet projet = (Projet) obj;
        return id == projet.id;
    }
}
