package ma.projet.exercice1.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> lignes;

    public Commande() {
    }

    // Constructeur avec paramètres
    public Commande(Date date, String numeroCommande) {
        this();
        this.date = date;

    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }

    // Méthodes utilitaires
    public void ajouterLigneCommande(LigneCommande ligne) {
        if (lignes == null) {
            lignes = new ArrayList<>();
        }
        lignes.add(ligne);
        ligne.setCommande(this);
    }

    public void retirerLigneCommande(LigneCommande ligne) {
        if (lignes != null) {
            lignes.remove(ligne);
            ligne.setCommande(null);
        }
    }



    public int getNombreArticles() {
        if (lignes == null || lignes.isEmpty()) {
            return 0;
        }

        return lignes.stream()
                .mapToInt(LigneCommande::getQuantite)
                .sum();
    }

    public boolean estVide() {
        return lignes == null || lignes.isEmpty();
    }



    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", date=" + date +

                ", nombreLignes=" + (lignes != null ? lignes.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Commande commande = (Commande) obj;
        return id == commande.id;
    }
}
