package ma.projet.exercice1.presentation;

import ma.projet.exercice1.classes.Categorie;
import ma.projet.exercice1.classes.Commande;
import ma.projet.exercice1.classes.LigneCommande;
import ma.projet.exercice1.classes.Produit;
import ma.projet.exercice1.dao.IDao;
import ma.projet.exercice1.dao.IProduitDao;
import ma.projet.exercice1.service.CategorieService;
import ma.projet.exercice1.service.CommandeService;
import ma.projet.exercice1.service.LigneCommandeService;
import ma.projet.exercice1.service.ProduitService;
import ma.projet.exercice1.util.HibernateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.List;

public class TestExercice1 {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateUtil.class);

        // Utiliser les noms de beans corrects
        IProduitDao produitDao = context.getBean("produitService", IProduitDao.class);
        IDao<Categorie> categorieDao = context.getBean("categorieService", IDao.class);
        IDao<Commande> commandeDao = context.getBean("commandeService", IDao.class);
        IDao<LigneCommande> ligneDao = context.getBean("ligneCommandeService", IDao.class);

        // ----- Add Categories -----
        Categorie cat1 = new Categorie();
        cat1.setCode("ELEC123");
        cat1.setLibelle("Électronique");
        categorieDao.create(cat1);

        Categorie cat2 = new Categorie();
        cat2.setCode("BOOK456");
        cat2.setLibelle("Livres");
        categorieDao.create(cat2);

        // ----- Produits -----
        Produit p1 = new Produit();
        p1.setReference("Smartphone");
        p1.setPrix(500.0);
        p1.setCategorie(cat1);
        produitDao.create(p1);

        Produit p2 = new Produit();
        p2.setReference("Laptop");
        p2.setPrix(1200.0);
        p2.setCategorie(cat1);
        produitDao.create(p2);

        Produit p3 = new Produit();
        p3.setReference("Java Book");
        p3.setPrix(150.0);
        p3.setCategorie(cat2);
        produitDao.create(p3);

        // ----- Commandes -----
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Commande cmd1 = new Commande();
        cmd1.setDate(sdf.parse("2025-10-10"));
        commandeDao.create(cmd1);

        Commande cmd2 = new Commande();
        cmd2.setDate(sdf.parse("2025-10-15"));
        commandeDao.create(cmd2);

        // ----- Lignes -----
        LigneCommande ligne1 = new LigneCommande();
        ligne1.setCommande(cmd1);
        ligne1.setProduit(p1);
        ligne1.setQuantite(7);
        ligneDao.create(ligne1);

        LigneCommande ligne2 = new LigneCommande();
        ligne2.setCommande(cmd1);
        ligne2.setProduit(p2);
        ligne2.setQuantite(11);
        ligneDao.create(ligne2);

        LigneCommande ligne3 = new LigneCommande();
        ligne3.setCommande(cmd1);
        ligne3.setProduit(p3);
        ligne3.setQuantite(2);
        ligneDao.create(ligne3);

        // ----- Test -----
        LigneCommande l = ligneDao.findById(2);
        System.out.println("Commande : " + l.getCommande().getId() + "\t Date: " +  l.getCommande().getDate());
        System.out.println("List des produits : ");
        System.out.println("Reference \t Prix \t Quantite");
        List<Produit> produitsCmd1 = produitDao.findProduitsByCommande(l.getCommande().getId());
        for (Produit p : produitsCmd1) {
            System.out.println(p.getReference() + " \t " + p.getPrix() + " \t " + produitDao.findQuantiteOfProduitsInLigne(p.getId()));
        }
        System.out.println("\n");

        // ----- Test search methods -----
        Categorie categorie = categorieDao.findById(1);
        List<Produit> produitsCat1 = produitDao.findProduitsByCategorie(categorie.getId());
        System.out.println("\n--- Produits par catégorie " + categorie.getLibelle() + " ---");
        for (Produit p : produitsCat1) {
            System.out.println(p.getReference() + " - " + p.getPrix());
        }

        System.out.println("\n--- Produits dont le prix > 100 ---");
        List<Produit> produitsPrixSup100 = produitDao.findProduitsPrixSup100();
        for (Produit p : produitsPrixSup100) {
            System.out.println(p.getReference() + " - " + p.getPrix());
        }

        System.out.println("\n--- Produits commandés entre 2025-10-09 et 2025-10-16 ---");
        List<Produit> produitsEntreDates = produitDao.findProduitsCommandesEntreDates(sdf.parse("2025-10-09"), sdf.parse("2025-10-16"));
        for (Produit p : produitsEntreDates) {
            System.out.println(p.getReference() + " - " + p.getPrix());
        }

        System.out.println("\n--- Produits de la commande 1 ---");
        List<Produit> produitsCmd1_2 = produitDao.findProduitsByCommande(commandeDao.findById(1).getId());
        for (Produit p : produitsCmd1_2) {
            System.out.println(p.getReference() + " - " + p.getPrix());
        }
    }
}