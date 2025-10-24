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

        // Clear existing data first
        clearDatabase(hommeService, femmeService, mariageService);

        createPersons(hommeService, femmeService);
        createMarriages(mariageService, hommeService, femmeService);
        executeTestQueries(hommeService, femmeService);
    }

    private static void clearDatabase(IHommeDao hommeService, IFemmeDao femmeService, IDao<Mariage> mariageService) {
        // Clear all data to avoid duplicates
        List<Mariage> mariages = mariageService.findAll();
        for (Mariage m : mariages) {
            mariageService.delete(m);
        }

        List<Homme> hommes = hommeService.findAll();
        for (Homme h : hommes) {
            hommeService.delete(h);
        }

        List<Femme> femmes = femmeService.findAll();
        for (Femme f : femmes) {
            femmeService.delete(f);
        }
    }

    private static void createPersons(IHommeDao hommeService, IFemmeDao femmeService) {
        // Création des hommes
        Homme homme1 = creerHomme("SAFI", "SAID", "0612345678", "Casablanca");
        hommeService.create(homme1);

        Homme homme2 = creerHomme("BENJELLOUN", "OMAR", "0623456789", "Rabat");
        hommeService.create(homme2);

        // Création des femmes
        Femme femme1 = creerFemme("SALIMA", "RAMI", "0645678901", "Casablanca");
        femmeService.create(femme1);

        Femme femme2 = creerFemme("AMAL", "ALI", "0656789012", "Rabat");
        femmeService.create(femme2);

        Femme femme3 = creerFemme("WAFA", "ALAOUI", "0667890123", "Fès");
        femmeService.create(femme3);

        Femme femme4 = creerFemme("KARIMA", "ALAMI", "0678901234", "Marrakech");
        femmeService.create(femme4);

        Femme femme5 = creerFemme("AICHA", "MANSOURI", "0689012345", "Tanger");
        femmeService.create(femme5);
    }

    private static void createMarriages(IDao<Mariage> mariageService, IHommeDao hommeService, IFemmeDao femmeService) {
        // Récupération des personnes créées par nom au lieu d'ID
        List<Homme> hommes = hommeService.findAll();
        List<Femme> femmes = femmeService.findAll();

        Homme safi = hommes.stream().filter(h -> "SAFI".equals(h.getNom())).findFirst().orElse(null);
        Homme benjelloun = hommes.stream().filter(h -> "BENJELLOUN".equals(h.getNom())).findFirst().orElse(null);

        Femme salima = femmes.stream().filter(f -> "SALIMA".equals(f.getNom())).findFirst().orElse(null);
        Femme amal = femmes.stream().filter(f -> "AMAL".equals(f.getNom())).findFirst().orElse(null);
        Femme wafa = femmes.stream().filter(f -> "WAFA".equals(f.getNom())).findFirst().orElse(null);
        Femme karima = femmes.stream().filter(f -> "KARIMA".equals(f.getNom())).findFirst().orElse(null);
        Femme aicha = femmes.stream().filter(f -> "AICHA".equals(f.getNom())).findFirst().orElse(null);

        if (safi == null || benjelloun == null || salima == null || amal == null ||
                wafa == null || karima == null || aicha == null) {
            System.out.println("Erreur: Impossible de trouver toutes les personnes créées");
            return;
        }

        // Premier mariage (divorcé) - SAFI avec KARIMA
        Mariage mariage1 = new Mariage();
        mariage1.setDateDebut(creerDate(1989, 8, 3));
        mariage1.setDateFin(creerDate(1990, 8, 3));
        mariage1.setNbrEnfant(0);
        mariage1.setHomme(safi);
        mariage1.setFemme(karima);
        mariageService.create(mariage1);

        // Deuxième mariage (actuel) - SAFI avec SALIMA
        Mariage mariage2 = new Mariage();
        mariage2.setDateDebut(creerDate(1990, 8, 3));
        mariage2.setNbrEnfant(4);
        mariage2.setHomme(safi);
        mariage2.setFemme(salima);
        mariageService.create(mariage2);

        // Troisième mariage (actuel) - SAFI avec AMAL
        Mariage mariage3 = new Mariage();
        mariage3.setDateDebut(creerDate(1995, 8, 3));
        mariage3.setNbrEnfant(2);
        mariage3.setHomme(safi);
        mariage3.setFemme(amal);
        mariageService.create(mariage3);

        // Quatrième mariage (actuel) - SAFI avec WAFA
        Mariage mariage4 = new Mariage();
        mariage4.setDateDebut(creerDate(2000, 10, 4));
        mariage4.setNbrEnfant(3);
        mariage4.setHomme(safi);
        mariage4.setFemme(wafa);
        mariageService.create(mariage4);

        // Cinquième mariage (divorcé) - BENJELLOUN avec AICHA
        Mariage mariage5 = new Mariage();
        mariage5.setDateDebut(creerDate(2015, 0, 1));
        mariage5.setDateFin(creerDate(2018, 0, 1));
        mariage5.setNbrEnfant(2);
        mariage5.setHomme(benjelloun);
        mariage5.setFemme(aicha);
        mariageService.create(mariage5);

        // Sixième mariage (actuel) - BENJELLOUN avec AICHA (pour avoir des femmes mariées plusieurs fois)
        Mariage mariage6 = new Mariage();
        mariage6.setDateDebut(creerDate(2019, 0, 1));
        mariage6.setNbrEnfant(1);
        mariage6.setHomme(benjelloun);
        mariage6.setFemme(aicha);
        mariageService.create(mariage6);
    }

    private static Date creerDate(int annee, int mois, int jour) {
        return new Date(annee - 1900, mois, jour);
    }

    private static Homme creerHomme(String nom, String prenom, String telephone, String adresse) {
        Homme homme = new Homme();
        homme.setNom(nom);
        homme.setPrenom(prenom);
        homme.setTelephone(telephone);
        homme.setAdresse(adresse);
        homme.setDateNaissance(creerDate(1960, 0, 1));
        return homme;
    }

    private static Femme creerFemme(String nom, String prenom, String telephone, String adresse) {
        Femme femme = new Femme();
        femme.setNom(nom);
        femme.setPrenom(prenom);
        femme.setTelephone(telephone);
        femme.setAdresse(adresse);
        femme.setDateNaissance(creerDate(1970, 0, 1));
        return femme;
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
        // Trouver SAFI par nom au lieu d'ID
        List<Homme> hommes = hommeService.findAll();
        Homme homme = hommes.stream().filter(h -> "SAFI".equals(h.getNom())).findFirst().orElse(null);

        if (homme == null) {
            System.out.println("===== Homme SAFI non trouvé =====");
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
        // Trouver SALIMA par nom au lieu d'ID
        List<Femme> femmes = femmeService.findAll();
        Femme femme = femmes.stream().filter(f -> "SALIMA".equals(f.getNom())).findFirst().orElse(null);

        if (femme == null) {
            System.out.println("==== Femme SALIMA non trouvée ====");
            return;
        }
        System.out.println("==== Le nombre d'enfants de " + femme.getNom() + " entre deux dates ====");
        // Dates corrigées: 2010-2026
        Date dateDebut = new Date(110, 0, 1); // 2010
        Date dateFin = new Date(126, 0, 1);   // 2026
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
        // Dates corrigées: 2010-2026
        Date dateDebut = new Date(110, 0, 1); // 2010
        Date dateFin = new Date(126, 0, 1);   // 2026
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
        // Trouver SAFI par nom au lieu d'ID
        List<Homme> hommes = hommeService.findAll();
        Homme homme = hommes.stream().filter(h -> "SAFI".equals(h.getNom())).findFirst().orElse(null);

        if (homme == null) {
            System.out.println("Homme SAFI non trouvé");
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