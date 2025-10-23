package ma.projet.exercice1.dao;

import ma.projet.exercice1.classes.Produit;

import java.util.Date;
import java.util.List;

public interface IProduitDao extends IDao<Produit> {
    List<Produit> findProduitsByCategorie(int idCategorie);
    List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin);
    List<Produit> findProduitsByCommande(int idCommande);
    List<Produit> findProduitsPrixSup100();
    int findQuantiteOfProduitsInLigne(int idProduit);
}
