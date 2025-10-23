package ma.projet.exercice3.presentation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ma.projet.exercice3.util.HibernateUtil;
import ma.projet.exercice3.beans.Homme;
import ma.projet.exercice3.beans.Femme;
import ma.projet.exercice3.beans.Mariage;
import ma.projet.exercice3.dao.IHommeDao;
import ma.projet.exercice3.dao.IFemmeDao;
import ma.projet.exercice3.dao.IDao;

import java.util.Date;
import java.util.List;

public class TestExercice3 {
    public static void main(String[] args) throws Exception {
        // Initialisation du contexte Spring
        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateUtil.class);

        // Récupération des services via l'injection de dépendance
        IHommeDao hommeService = context.getBean("hommeService", IHommeDao.class);
        IFemmeDao femmeService = context.getBean("femmeService", IFemmeDao.class);
        IDao<Mariage> mariageService = context.getBean("mariageService", IDao.class);

        // Création des instances d'hommes
        Homme premierHomme = creerHomme("Amrani", "Youssef", "0600000000", "adresse1");
        hommeService.create(premierHomme);

        Homme deuxiemeHomme = creerHomme("Fakir", "Mehdi", "0600000000", "adresse2");
        hommeService.create(deuxiemeHomme);

        Homme troisiemeHomme = creerHomme("Tazi", "Anas", "0600000000", "adresse3");
        hommeService.create(troisiemeHomme);

        Homme quatriemeHomme = creerHomme("Ouazzani", "Karim", "0600000000", "adresse4");
        hommeService.create(quatriemeHomme);

        Homme cinquiemeHomme = creerHomme("Benomar", "Rachid", "0600000000", "adresse5");
        hommeService.create(cinquiemeHomme);

        // Création des instances de femmes
        Femme premiereFemme = creerFemme("Berrada", "Lina", "0600000000", "adresse1");
        femmeService.create(premiereFemme);

        Femme deuxiemeFemme = creerFemme("Mansouri", "Sofia", "0600000000", "adresse2");
        femmeService.create(deuxiemeFemme);

        Femme troisiemeFemme = creerFemme("Haddad", "Amal", "0600000000", "adresse3");
        femmeService.create(troisiemeFemme);

        Femme quatriemeFemme = creerFemme("Cherkaoui", "Yasmina", "0600000000", "adresse4");
        femmeService.create(quatriemeFemme);

        Femme cinquiemeFemme = creerFemme("Idrissi", "Salma", "0600000000", "adresse5");
        femmeService.create(cinquiemeFemme);

        Femme sixiemeFemme = creerFemme("Rahimi", "Nawal", "0600000000", "adresse6");
        femmeService.create(sixiemeFemme);

        Femme septiemeFemme = creerFemme("Khatib", "Laila", "0600000000", "adresse7");
        femmeService.create(septiemeFemme);

        Femme huitiemeFemme = creerFemme("Benali", "Imane", "0600000000", "adresse8");
        femmeService.create(huitiemeFemme);

        Femme neuviemeFemme = creerFemme("Douiri", "Amina", "0600000000", "adresse9");
        femmeService.create(neuviemeFemme);

        Femme dixiemeFemme = creerFemme("Zahraoui", "Karima", "0600000000", "adresse10");
        femmeService.create(dixiemeFemme);

        // Création des mariages
        Mariage premierMariage = creerMariage(premierHomme, premiereFemme, 2);
        mariageService.create(premierMariage);

        Mariage deuxiemeMariage = creerMariage(deuxiemeHomme, deuxiemeFemme, 1);
        mariageService.create(deuxiemeMariage);

        Mariage troisiemeMariage = creerMariage(troisiemeHomme, troisiemeFemme, 2);
        mariageService.create(troisiemeMariage);

        Mariage quatriemeMariage = creerMariage(quatriemeHomme, quatriemeFemme, 1);
        mariageService.create(quatriemeMariage);

        Mariage cinquiemeMariage = creerMariage(cinquiemeHomme, cinquiemeFemme, 2);
        mariageService.create(cinquiemeMariage);

        // Affichage des résultats des requêtes
        afficherListeFemmes(femmeService);
        afficherFemmeLaPlusAgee(femmeService);
        afficherEpousesDUnHomme(hommeService);
        afficherNombreEnfantsEntreDates(femmeService);
        afficherFemmesMarieesDeuxFois(femmeService);
        afficherHommesMarieQuatreFemmes(hommeService);
        afficherDetailsMariages(hommeService);
    }

    // Méthodes utilitaires pour la création d'objets
    private static Homme creerHomme(String nom, String prenom, String telephone, String adresse) {
        Homme homme = new Homme();
        homme.setNom(nom);
        homme.setPrenom(prenom);
        homme.setTelephone(telephone);
        homme.setAdresse(adresse);
        homme.setDateNaissance(new Date());
        return homme;
    }

    private static Femme creerFemme(String nom, String prenom, String telephone, String adresse) {
        Femme femme = new Femme();
        femme.setNom(nom);
        femme.setPrenom(prenom);
        femme.setTelephone(telephone);
        femme.setAdresse(adresse);
        femme.setDateNaissance(new Date());
        return femme;
    }

    private static Mariage creerMariage(Homme homme, Femme femme, int nombreEnfants) {
        Mariage mariage = new Mariage();
        mariage.setDateDebut(new Date());
        mariage.setDateFin(new Date());
        mariage.setHomme(homme);
        mariage.setFemme(femme);
        mariage.setNbrEnfant(nombreEnfants);
        return mariage;
    }

    // Méthodes d'affichage des résultats
    private static void afficherListeFemmes(IFemmeDao femmeService) {
        System.out.println("==== La liste des femmes ====");
        List<Femme> toutesLesFemmes = femmeService.findAll();
        for (Femme femme : toutesLesFemmes) {
            System.out.println(femme);
        }
    }

    private static void afficherFemmeLaPlusAgee(IFemmeDao femmeService) {
        System.out.println("==== La femme la plus âgée ====");
        Femme femmeLaPlusAgee = femmeService.findFemmeLaPlusAgee();
        System.out.println(femmeLaPlusAgee);
    }

    private static void afficherEpousesDUnHomme(IHommeDao hommeService) {
        Homme homme = hommeService.findById(1);
        System.out.println("===== Les épouses de : " + homme.getNom() + " =====");
        List<Femme> epouses = hommeService.findEpouses(homme.getId());
        for (Femme femme : epouses) {
            System.out.println(femme);
        }
    }

    private static void afficherNombreEnfantsEntreDates(IFemmeDao femmeService) {
        Femme femme = femmeService.findById(6);
        System.out.println("==== Le nombre d'enfants de " + femme.getNom() + " entre deux dates ====");
        Date dateDebut = new Date(110, 0, 1);
        Date dateFin = new Date(126, 0, 1);
        int nombreEnfants = femmeService.countEnfantsEntreDates(femme.getId(), dateDebut, dateFin);
        System.out.println(nombreEnfants);
    }

    private static void afficherFemmesMarieesDeuxFois(IFemmeDao femmeService) {
        System.out.println("====  Les femmes mariées deux fois ou plus =====");
        List<Femme> femmesMarieesDeuxFois = femmeService.findFemmesMarieesDeuxFois();
        for (Femme femme : femmesMarieesDeuxFois) {
            System.out.println(femme);
        }
    }

    private static void afficherHommesMarieQuatreFemmes(IHommeDao hommeService) {
        System.out.println("==== Les hommes mariés à quatre femmes entre deux dates ====");
        Date dateDebut = new Date(110, 0, 1);
        Date dateFin = new Date(126, 0, 1);
        List<Homme> hommesMarieQuatreFemmes = hommeService.countHommesMarieQuatreFemmesEntreDates(dateDebut, dateFin);
        for (Homme homme : hommesMarieQuatreFemmes) {
            System.out.println(homme);
        }
    }

    private static void afficherDetailsMariages(IHommeDao hommeService) {
        System.out.println("==== Les mariages d'un homme avec tous les détails ====");
        Homme homme = hommeService.findById(1);
        System.out.println("Nom: " + homme.getNom() + " " + homme.getPrenom());

        System.out.println("Mariages En Cours :");
        List<Object[]> mariagesDetails = hommeService.findMariagesDetails(homme.getId());
        for (Object[] details : mariagesDetails) {
            System.out.println("Femme: " + details[0] + " " + details[1] + "\t Date Debut: " + details[2] + "\t Nombre d'enfants: " + details[3]);
        }

        System.out.println("Mariages échoués :");
        List<Object[]> mariagesEchoues = hommeService.findMariagesEchoues(homme.getId());
        for (Object[] details : mariagesEchoues) {
            System.out.println("Femme: " + details[0] + " " + details[1] + "\t Date Debut: " + details[2] + "\t Date Fin: " + details[3] + "\t Nombre d'enfants: " + details[4]);
        }
    }
}