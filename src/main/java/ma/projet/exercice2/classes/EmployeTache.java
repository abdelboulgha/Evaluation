package ma.projet.exercice2.classes;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class EmployeTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateDebutReelle;
    private Date dateFinReelle;

    @ManyToOne
    @JoinColumn(name="employe_id")
    private Employe employe;

    @ManyToOne
    @JoinColumn(name="tache_id")
    private Tache tache;

    public EmployeTache() {

    }

    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle) {
        this();
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }




    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }



    public long getDureeEnJours() {
        if (dateDebutReelle != null && dateFinReelle != null) {
            return (dateFinReelle.getTime() - dateDebutReelle.getTime()) / (1000 * 60 * 60 * 24);
        }
        return 0;
    }

    public boolean estValide() {
        return employe != null && tache != null && dateDebutReelle != null;
    }

    @Override
    public String toString() {
        return "EmployeTache{" +
                "id=" + id +

                ", dateDebutReelle=" + dateDebutReelle +
                ", dateFinReelle=" + dateFinReelle +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeTache that = (EmployeTache) obj;
        return id == that.id;
    }
}
