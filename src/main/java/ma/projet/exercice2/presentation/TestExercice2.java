package ma.projet.exercice2.presentation;

import ma.projet.exercice2.classes.Employe;
import ma.projet.exercice2.classes.EmployeTache;
import ma.projet.exercice2.classes.Projet;
import ma.projet.exercice2.classes.Tache;
import ma.projet.exercice2.dao.IDao;
import ma.projet.exercice2.dao.IEmployeDao;
import ma.projet.exercice2.dao.IProjetDao;
import ma.projet.exercice2.dao.ITacheDao;
import ma.projet.exercice2.util.HibernateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

public class TestExercice2 {

    public static void main(String[] args) throws Exception {
        // Initialisation du contexte Spring
        ApplicationContext springContext = new AnnotationConfigApplicationContext(HibernateUtil.class);

        // Récupération des services via Spring
        IEmployeDao employeDao = springContext.getBean("employeServcie", IEmployeDao.class);
        IProjetDao projetDao = springContext.getBean("projetService", IProjetDao.class);
        ITacheDao tacheDao = springContext.getBean("tacheService", ITacheDao.class);
        IDao<EmployeTache> employeTacheDao = springContext.getBean("employeTacheService", IDao.class);

        // Création des employés
        createEmployees(employeDao);

        // Création des projets
        createProjects(projetDao, employeDao);

        // Création des tâches
        createTasks(tacheDao, projetDao);

        // Création des assignations employé-tâche
        createEmployeeTaskAssignments(employeTacheDao, employeDao, tacheDao);

        // Exécution des requêtes de test
        executeTestQueries(employeDao, projetDao, tacheDao);
    }

    private static void createEmployees(IEmployeDao employeDao) {
        // Premier employé
        Employe premierEmploye = new Employe();
        premierEmploye.setNom("Elhamri");
        premierEmploye.setPrenom("Ahmed");
        premierEmploye.setTelephone("0600000000");
        employeDao.create(premierEmploye);

        // Deuxième employé
        Employe deuxiemeEmploye = new Employe();
        deuxiemeEmploye.setNom("Bennani");
        deuxiemeEmploye.setPrenom("Youssef");
        deuxiemeEmploye.setTelephone("0600000000");
        employeDao.create(deuxiemeEmploye);
    }

    private static void createProjects(IProjetDao projetDao, IEmployeDao employeDao) {
        Projet projetRH = new Projet();
        projetRH.setNom("Système RH");
        projetRH.setDateDebut(new Date());
        projetRH.setDateFin(new Date());

        // Récupération du premier employé pour le chef de projet
        Employe chefProjet = employeDao.findById(1);
        projetRH.setChef(chefProjet);

        projetDao.create(projetRH);
    }

    private static void createTasks(ITacheDao tacheDao, IProjetDao projetDao) {
        // Première tâche
        Tache tacheBackend = new Tache();
        tacheBackend.setNom("Backend API");
        tacheBackend.setNom("Développer l'API du module RH");
        tacheBackend.setDateDebut(new Date());
        tacheBackend.setDateFin(new Date());
        tacheBackend.setPrix(200);

        Projet projetAssocie = projetDao.findById(1);
        tacheBackend.setProjet(projetAssocie);
        tacheDao.create(tacheBackend);

        // Deuxième tâche
        Tache tacheFrontend = new Tache();
        tacheFrontend.setNom("Frontend React");
        tacheFrontend.setDateDebut(new Date());
        tacheFrontend.setDateFin(new Date());
        tacheFrontend.setPrix(200);
        tacheFrontend.setProjet(projetAssocie);
        tacheDao.create(tacheFrontend);
    }

    private static void createEmployeeTaskAssignments(IDao<EmployeTache> employeTacheDao,
                                                      IEmployeDao employeDao,
                                                      ITacheDao tacheDao) {
        // Première assignation
        EmployeTache assignation1 = new EmployeTache();
        assignation1.setDateDebutReelle(new Date());
        assignation1.setDateFinReelle(new Date());
        assignation1.setEmploye(employeDao.findById(2));
        assignation1.setTache(tacheDao.findById(1));
        employeTacheDao.create(assignation1);

        // Deuxième assignation
        EmployeTache assignation2 = new EmployeTache();
        assignation2.setDateDebutReelle(new Date());
        assignation2.setDateFinReelle(new Date());
        assignation2.setEmploye(employeDao.findById(2));
        assignation2.setTache(tacheDao.findById(2));
        employeTacheDao.create(assignation2);
    }

    private static void executeTestQueries(IEmployeDao employeDao, IProjetDao projetDao, ITacheDao tacheDao) {
        // Test 1: Tâches réalisées par un employé
        testTachesRealiseesParEmploye(employeDao);

        // Test 2: Projets gérés par un employé
        testProjetsGeresParEmploye(employeDao);

        // Test 3: Tâches planifiées par projet
        testTachesPlanifieesParProjet(projetDao);

        // Test 4: Tâches réalisées par projet
        testTachesRealiseesParProjet(projetDao);

        // Test 5: Tâches avec prix supérieur à 1000
        testTachesPrixSuperieur1000(tacheDao);

        // Test 6: Tâches entre deux dates
        testTachesEntreDates(tacheDao);

        // Test 7: Affichage détaillé
        affichageDetailleProjet(projetDao);
    }

    private static void testTachesRealiseesParEmploye(IEmployeDao employeDao) {
        Employe employeRecherche = employeDao.findById(2);
        List<Tache> listeTaches = employeDao.findTachesRealiseesByEmploye(employeRecherche.getId());

        System.out.println("==== Taches Realisees par " + employeRecherche.getNom() + " ====");
        for (Tache tache : listeTaches) {
            System.out.println(tache.getNom());
        }
    }

    private static void testProjetsGeresParEmploye(IEmployeDao employeDao) {
        Employe employeRecherche = employeDao.findById(1);
        List<Projet> listeProjets = employeDao.findProjetsGeresByEmploye(employeRecherche.getId());

        System.out.println("==== Projets Geres par " + employeRecherche.getNom() + " ====");
        for (Projet projet : listeProjets) {
            System.out.println(projet.getNom());
        }
    }

    private static void testTachesPlanifieesParProjet(IProjetDao projetDao) {
        Projet projetRecherche = projetDao.findById(1);
        System.out.println("==== Taches Planifiees par " + projetRecherche.getNom() + " ====");

        List<Tache> listeTaches = projetDao.findTachesPlanifieesByProjet(projetRecherche.getId());
        for (Tache tache : listeTaches) {
            System.out.println(tache.getNom());
        }
    }

    private static void testTachesRealiseesParProjet(IProjetDao projetDao) {
        Projet projetRecherche = projetDao.findById(1);
        System.out.println("==== Taches Realisees par " + projetRecherche.getNom() + " ====");

        List<Tache> listeTaches = projetDao.findTachesRealiseesByProjet(projetRecherche.getId());
        for (Tache tache : listeTaches) {
            System.out.println(tache.getNom());
        }
    }

    private static void testTachesPrixSuperieur1000(ITacheDao tacheDao) {
        System.out.println("==== Prix Des Taches Superieur a 1000 ====");
        List<Tache> listeTaches = tacheDao.findTachesPrixSup1000();
        for (Tache tache : listeTaches) {
            System.out.println(tache.getNom());
        }
    }

    private static void testTachesEntreDates(ITacheDao tacheDao) {
        System.out.println("==== Taches Entre Deux Dates ====");
        Date dateDebut = new Date(2025, 10, 1, 0, 0, 0);
        Date dateFin = new Date(2025, 10, 30, 0, 0, 0);

        List<Tache> listeTaches = tacheDao.findTachesRealiseesEntreDates(dateDebut, dateFin);
        for (Tache tache : listeTaches) {
            System.out.println(tache.getNom());
        }
    }

    private static void affichageDetailleProjet(IProjetDao projetDao) {
        System.out.println("==== Exemple d'affichage ====");
        Projet projetDetaille = projetDao.findById(1);

        System.out.println("Projet: " + projetDetaille.getId() + "\t Nom: " +
                projetDetaille.getNom() + "\t Date debut: " +
                projetDetaille.getDateDebut());

        System.out.println("Liste des taches: ");
        System.out.println("Num \t Nom \t Date Debut Reelle \t Date Fin Reelle");

        List<Tache> tachesProjet = projetDao.findTachesRealiseesByProjet(projetDetaille.getId());
        for (Tache tache : tachesProjet) {
            System.out.println(tache.getId() + " \t " + tache.getNom() +
                    " \t " + tache.getDateDebut() + " \t " + tache.getDateFin());
        }
    }
}