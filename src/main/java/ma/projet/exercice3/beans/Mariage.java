package ma.projet.exercice3.beans;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private int nbrEnfant;

    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    public Mariage() {
        this.nbrEnfant = 0;
    }

    public Mariage(Date dateDebut, Homme homme, Femme femme) {
        this();
        this.dateDebut = dateDebut;
        this.homme = homme;
        this.femme = femme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNbrEnfant() {
        return nbrEnfant;
    }

    public void setNbrEnfant(int nbrEnfant) {
        this.nbrEnfant = nbrEnfant;
    }

    public Homme getHomme() {
        return homme;
    }

    public void setHomme(Homme homme) {
        this.homme = homme;
    }

    public Femme getFemme() {
        return femme;
    }

    public void setFemme(Femme femme) {
        this.femme = femme;
    }

    public void ajouterEnfant() {
        this.nbrEnfant++;
    }

    public void retirerEnfant() {
        if (this.nbrEnfant > 0) {
            this.nbrEnfant--;
        }
    }

    public void terminerMariage(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean estActif() {
        return dateFin == null;
    }

    public boolean estTermine() {
        return dateFin != null;
    }

    public long getDureeEnJours() {
        if (dateDebut == null) {
            return 0;
        }
        Date fin = dateFin != null ? dateFin : new Date();
        return (fin.getTime() - dateDebut.getTime()) / (1000 * 60 * 60 * 24);
    }

    public long getDureeEnAnnees() {
        return getDureeEnJours() / 365;
    }

    public boolean estValide() {
        return dateDebut != null && homme != null && femme != null && nbrEnfant >= 0;
    }

    public boolean aDesEnfants() {
        return nbrEnfant > 0;
    }

    public boolean estSterile() {
        return nbrEnfant == 0;
    }

    public void divorcer() {
        this.dateFin = new Date();
    }

    public boolean estDivorce() {
        return dateFin != null;
    }

    @Override
    public String toString() {
        return "Mariage{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbrEnfant=" + nbrEnfant +
                ", actif=" + estActif() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mariage mariage = (Mariage) obj;
        return id == mariage.id;
    }
}
