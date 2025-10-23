package ma.projet.exercice1.classes;

import jakarta.persistence.*;

import java.util.List;

@NamedQuery(
        name = "Produit.findPrixSup100",
        query = "from Produit p where p.prix > 100"
)
@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private double prix;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<LigneCommande> lignes;

    public Produit() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String name) {
        this.reference = name;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double price) {
        this.prix = price;
    }

    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }
}
