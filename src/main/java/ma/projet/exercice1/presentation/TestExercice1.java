package ma.projet.exercice1.presentation;

import ma.projet.exercice1.classes.Categorie;
import ma.projet.exercice1.classes.Commande;
import ma.projet.exercice1.classes.LigneCommande;
import ma.projet.exercice1.classes.Produit;
import ma.projet.exercice1.dao.IDao;
import ma.projet.exercice1.dao.IProduitDao;
import ma.projet.exercice1.util.HibernateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.List;

public class TestExercice1 {

    public static void main(String[] args) throws Exception {
        // Initialisation du contexte Spring
        ApplicationContext springContext = new AnnotationConfigApplicationContext(HibernateUtil.class);

        // Récupération des services via l'injection de dépendances
        IProduitDao serviceProduit = springContext.getBean("produitService", IProduitDao.class);
        IDao<Categorie> serviceCategorie = springContext.getBean("categorieService", IDao.class);
        IDao<Commande> serviceCommande = springContext.getBean("commandeService", IDao.class);
        IDao<LigneCommande> serviceLigneCommande = springContext.getBean("ligneCommandeService", IDao.class);

        // === Création des catégories ===
        Categorie categorieElectronique = new Categorie();
        categorieElectronique.setCode("ELEC123");
        categorieElectronique.setLibelle("Électronique");
        serviceCategorie.create(categorieElectronique);

        Categorie categorieLivres = new Categorie();
        categorieLivres.setCode("BOOK456");
        categorieLivres.setLibelle("Livres");
        serviceCategorie.create(categorieLivres);

        // === Création des produits ===
        Produit smartphone = new Produit();
        smartphone.setReference("Smartphone");
        smartphone.setPrix(500.0);
        smartphone.setCategorie(categorieElectronique);
        serviceProduit.create(smartphone);

        Produit laptop = new Produit();
        laptop.setReference("Laptop");
        laptop.setPrix(1200.0);
        laptop.setCategorie(categorieElectronique);
        serviceProduit.create(laptop);

        Produit livreJava = new Produit();
        livreJava.setReference("Java Book");
        livreJava.setPrix(150.0);
        livreJava.setCategorie(categorieLivres);
        serviceProduit.create(livreJava);

        // === Création des commandes ===
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Commande commande1 = new Commande();
        commande1.setDate(formatDate.parse("2025-10-10"));
        serviceCommande.create(commande1);

        Commande commande2 = new Commande();
        commande2.setDate(formatDate.parse("2025-10-15"));
        serviceCommande.create(commande2);

        // === Création des lignes de commande ===
        LigneCommande ligneCommande1 = new LigneCommande();
        ligneCommande1.setCommande(commande1);
        ligneCommande1.setProduit(smartphone);
        ligneCommande1.setQuantite(7);
        serviceLigneCommande.create(ligneCommande1);

        LigneCommande ligneCommande2 = new LigneCommande();
        ligneCommande2.setCommande(commande1);
        ligneCommande2.setProduit(laptop);
        ligneCommande2.setQuantite(11);
        serviceLigneCommande.create(ligneCommande2);

        LigneCommande ligneCommande3 = new LigneCommande();
        ligneCommande3.setCommande(commande1);
        ligneCommande3.setProduit(livreJava);
        ligneCommande3.setQuantite(2);
        serviceLigneCommande.create(ligneCommande3);

        // === Test d'affichage des produits d'une commande ===
        LigneCommande ligneTest = serviceLigneCommande.findById(2);
        System.out.println("Commande : " + ligneTest.getCommande().getId() + "\t Date: " + ligneTest.getCommande().getDate());
        System.out.println("Liste des produits : ");
        System.out.println("Référence \t Prix \t Quantité");
        List<Produit> produitsCommande = serviceProduit.findProduitsByCommande(ligneTest.getCommande().getId());
        for (Produit produit : produitsCommande) {
            System.out.println(produit.getReference() + " \t " + produit.getPrix() + " \t " +
                    serviceProduit.findQuantiteOfProduitsInLigne(produit.getId()));
        }
        System.out.println("\n");

        // === Test des méthodes de recherche ===
        Categorie categorieTest = serviceCategorie.findById(1);
        List<Produit> produitsParCategorie = serviceProduit.findProduitsByCategorie(categorieTest.getId());
        System.out.println("\n--- Produits par catégorie " + categorieTest.getLibelle() + " ---");
        for (Produit produit : produitsParCategorie) {
            System.out.println(produit.getReference() + " - " + produit.getPrix());
        }

        System.out.println("\n--- Produits dont le prix > 100 ---");
        List<Produit> produitsPrixSuperieur100 = serviceProduit.findProduitsPrixSup100();
        for (Produit produit : produitsPrixSuperieur100) {
            System.out.println(produit.getReference() + " - " + produit.getPrix());
        }

        System.out.println("\n--- Produits commandés entre 2025-10-09 et 2025-10-16 ---");
        List<Produit> produitsEntreDates = serviceProduit.findProduitsCommandesEntreDates(
                formatDate.parse("2025-10-09"), formatDate.parse("2025-10-16"));
        for (Produit produit : produitsEntreDates) {
            System.out.println(produit.getReference() + " - " + produit.getPrix());
        }

        System.out.println("\n--- Produits de la commande 1 ---");
        List<Produit> produitsCommande1 = serviceProduit.findProduitsByCommande(serviceCommande.findById(1).getId());
        for (Produit produit : produitsCommande1) {
            System.out.println(produit.getReference() + " - " + produit.getPrix());
        }
    }
}