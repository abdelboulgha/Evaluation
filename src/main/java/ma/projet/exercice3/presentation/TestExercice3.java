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
        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateUtil.class);

        IHommeDao hommeService = context.getBean("hommeService", IHommeDao.class);
        IFemmeDao femmeService = context.getBean("femmeService", IFemmeDao.class);
        IDao<Mariage> mariageService = context.getBean("mariageService", IDao.class);

        createPersons(hommeService, femmeService);
        createMarriages(mariageService, hommeService, femmeService);
        executeTestQueries(hommeService, femmeService);
    }

    private static void createPersons(IHommeDao hommeService, IFemmeDao femmeService) {
        // Création des hommes
        Homme homme1 = creerHomme("Alaoui", "Mohamed", "0612345678", "Casablanca");
        hommeService.create(homme1);

        Homme homme2 = creerHomme("Benjelloun", "Omar", "0623456789", "Rabat");
        hommeService.create(homme2);

        Homme homme3 = creerHomme("Chraibi", "Hassan", "0634567890", "Fès");
        hommeService.create(homme3);

        // Création des femmes
        Femme femme1 = creerFemme("Berrada", "Fatima", "0645678901", "Casablanca");
        femmeService.create(femme1);

        Femme femme2 = creerFemme("Mansouri", "Aicha", "0656789012", "Rabat");
        femmeService.create(femme2);

        Femme femme3 = creerFemme("Haddad", "Khadija", "0667890123", "Fès");
        femmeService.create(femme3);

        Femme femme4 = creerFemme("Cherkaoui", "Zineb", "0678901234", "Marrakech");
        femmeService.create(femme4);

        Femme femme5 = creerFemme("Idrissi", "Naima", "0689012345", "Tanger");
        femmeService.create(femme5);
    }

    private static void createMarriages(IDao<Mariage> mariageService, IHommeDao hommeService, IFemmeDao femmeService) {
        // Premier mariage
        Mariage mariage1 = creerMariage(hommeService.findById(1), femmeService.findById(1), 3);
        mariageService.create(mariage1);

        // Deuxième mariage (divorcé)
        Mariage mariage2 = creerMariage(hommeService.findById(1), femmeService.findById(2), 2);
        mariage2.setDateFin(new Date());
        mariageService.create(mariage2);

        // Troisième mariage
        Mariage mariage3 = creerMariage(hommeService.findById(2), femmeService.findById(3), 1);
        mariageService.create(mariage3);

        // Quatrième mariage (divorcé)
        Mariage mariage4 = creerMariage(hommeService.findById(2), femmeService.findById(4), 0);
        mariage4.setDateFin(new Date());
        mariageService.create(mariage4);

        // Cinquième mariage
        Mariage mariage5 = creerMariage(hommeService.findById(3), femmeService.findById(5), 4);
        mariageService.create(mariage5);
    }

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
        mariage.setHomme(homme);
        mariage.setFemme(femme);
        mariage.setNbrEnfant(nombreEnfants);
        return mariage;
    }

    private static void executeTestQueries(IHommeDao hommeService, IFemmeDao femmeService) {
        afficherListeFemmes(femmeService);
        afficherFemmeLaPlusAgee(femmeService);
        afficherEpousesDUnHomme(hommeService);
        afficherNombreEnfantsEntreDates(femmeService);
        afficherFemmesMarieesDeuxFois(femmeService);
        afficherHommesMarieQuatreFemmes(hommeService);
        afficherDetailsMariages(hommeService);
    }

    private static void afficherListeFemmes(IFemmeDao femmeService) {
        System.out.println("==== La liste des femmes ====");
        List<Femme> toutesLesFemmes = femmeService.findAll();
        if (toutesLesFemmes.isEmpty()) {
            System.out.println("Aucune femme trouvée");
        } else {
            for (Femme femme : toutesLesFemmes) {
                System.out.println(femme);
            }
        }
    }

    private static void afficherFemmeLaPlusAgee(IFemmeDao femmeService) {
        System.out.println("==== La femme la plus âgée ====");
        Femme femmeLaPlusAgee = femmeService.findFemmeLaPlusAgee();
        if (femmeLaPlusAgee == null) {
            System.out.println("Aucune femme trouvée");
        } else {
            System.out.println(femmeLaPlusAgee);
        }
    }

    private static void afficherEpousesDUnHomme(IHommeDao hommeService) {
        Homme homme = hommeService.findById(1);
        if (homme == null) {
            System.out.println("===== Homme non trouvé =====");
            return;
        }
        System.out.println("===== Les épouses de : " + homme.getNom() + " =====");
        List<Femme> epouses = hommeService.findEpouses(homme.getId());
        if (epouses.isEmpty()) {
            System.out.println("Aucune épouse trouvée");
        } else {
            for (Femme femme : epouses) {
                System.out.println(femme);
            }
        }
    }

    private static void afficherNombreEnfantsEntreDates(IFemmeDao femmeService) {
        Femme femme = femmeService.findById(1);
        if (femme == null) {
            System.out.println("==== Femme non trouvée ====");
            return;
        }
        System.out.println("==== Le nombre d'enfants de " + femme.getNom() + " entre deux dates ====");
        Date dateDebut = new Date(110, 0, 1);
        Date dateFin = new Date(126, 0, 1);
        int nombreEnfants = femmeService.countEnfantsEntreDates(femme.getId(), dateDebut, dateFin);
        System.out.println("Nombre d'enfants: " + nombreEnfants);
    }

    private static void afficherFemmesMarieesDeuxFois(IFemmeDao femmeService) {
        System.out.println("==== Les femmes mariées deux fois ou plus =====");
        List<Femme> femmesMarieesDeuxFois = femmeService.findFemmesMarieesDeuxFois();
        if (femmesMarieesDeuxFois.isEmpty()) {
            System.out.println("Aucune femme mariée deux fois ou plus trouvée");
        } else {
            for (Femme femme : femmesMarieesDeuxFois) {
                System.out.println(femme);
            }
        }
    }

    private static void afficherHommesMarieQuatreFemmes(IHommeDao hommeService) {
        System.out.println("==== Les hommes mariés à quatre femmes entre deux dates ====");
        Date dateDebut = new Date(110, 0, 1);
        Date dateFin = new Date(126, 0, 1);
        List<Homme> hommesMarieQuatreFemmes = hommeService.countHommesMarieQuatreFemmesEntreDates(dateDebut, dateFin);
        if (hommesMarieQuatreFemmes.isEmpty()) {
            System.out.println("Aucun homme marié à quatre femmes trouvé");
        } else {
            for (Homme homme : hommesMarieQuatreFemmes) {
                System.out.println(homme);
            }
        }
    }

    private static void afficherDetailsMariages(IHommeDao hommeService) {
        System.out.println("==== Les mariages d'un homme avec tous les détails ====");
        Homme homme = hommeService.findById(1);
        if (homme == null) {
            System.out.println("Homme non trouvé");
            return;
        }
        System.out.println("Nom: " + homme.getNom() + " " + homme.getPrenom());

        System.out.println("Mariages En Cours :");
        List<Object[]> mariagesDetails = hommeService.findMariagesDetails(homme.getId());
        if (mariagesDetails.isEmpty()) {
            System.out.println("Aucun mariage en cours trouvé");
        } else {
            for (Object[] details : mariagesDetails) {
                System.out.println("Femme: " + details[0] + " " + details[1] + "\t Date Debut: " + details[2] + "\t Nombre d'enfants: " + details[3]);
            }
        }

        System.out.println("Mariages échoués :");
        List<Object[]> mariagesEchoues = hommeService.findMariagesEchoues(homme.getId());
        if (mariagesEchoues.isEmpty()) {
            System.out.println("Aucun mariage échoué trouvé");
        } else {
            for (Object[] details : mariagesEchoues) {
                System.out.println("Femme: " + details[0] + " " + details[1] + "\t Date Debut: " + details[2] + "\t Date Fin: " + details[3] + "\t Nombre d'enfants: " + details[4]);
            }
        }
    }
}