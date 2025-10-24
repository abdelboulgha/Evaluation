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
        ApplicationContext springContext = new AnnotationConfigApplicationContext(HibernateUtil.class);

        IEmployeDao employeDao = springContext.getBean("employeService", IEmployeDao.class);
        IProjetDao projetDao = springContext.getBean("projetService", IProjetDao.class);
        ITacheDao tacheDao = springContext.getBean("tacheService", ITacheDao.class);
        IDao<EmployeTache> employeTacheDao = springContext.getBean("employeTacheService", IDao.class);

        createEmployees(employeDao);
        createProjects(projetDao, employeDao);
        createTasks(tacheDao, projetDao);
        createEmployeeTaskAssignments(employeTacheDao, employeDao, tacheDao);
        executeTestQueries(employeDao, projetDao, tacheDao);
    }

    private static void createEmployees(IEmployeDao employeDao) {
        Employe employe1 = new Employe();
        employe1.setNom("Alami");
        employe1.setPrenom("Fatima");
        employe1.setTelephone("0612345678");
        employeDao.create(employe1);

        Employe employe2 = new Employe();
        employe2.setNom("Benjelloun");
        employe2.setPrenom("Omar");
        employe2.setTelephone("0623456789");
        employeDao.create(employe2);

        Employe employe3 = new Employe();
        employe3.setNom("Chraibi");
        employe3.setPrenom("Aicha");
        employe3.setTelephone("0634567890");
        employeDao.create(employe3);
    }

    private static void createProjects(IProjetDao projetDao, IEmployeDao employeDao) {
        Projet projet1 = new Projet();
        projet1.setNom("Application E-commerce");
        projet1.setDateDebut(new Date());
        projet1.setDateFin(new Date());
        projet1.setChef(employeDao.findById(1));
        projetDao.create(projet1);

        Projet projet2 = new Projet();
        projet2.setNom("Système de Gestion");
        projet2.setDateDebut(new Date());
        projet2.setDateFin(new Date());
        projet2.setChef(employeDao.findById(2));
        projetDao.create(projet2);
    }

    private static void createTasks(ITacheDao tacheDao, IProjetDao projetDao) {
        Tache tache1 = new Tache();
        tache1.setNom("Développement Backend");
        tache1.setDateDebut(new Date());
        tache1.setDateFin(new Date());
        tache1.setPrix(1500);
        tache1.setProjet(projetDao.findById(1));
        tacheDao.create(tache1);

        Tache tache2 = new Tache();
        tache2.setNom("Interface Utilisateur");
        tache2.setDateDebut(new Date());
        tache2.setDateFin(new Date());
        tache2.setPrix(800);
        tache2.setProjet(projetDao.findById(1));
        tacheDao.create(tache2);

        Tache tache3 = new Tache();
        tache3.setNom("Tests et Déploiement");
        tache3.setDateDebut(new Date());
        tache3.setDateFin(new Date());
        tache3.setPrix(1200);
        tache3.setProjet(projetDao.findById(2));
        tacheDao.create(tache3);
    }

    private static void createEmployeeTaskAssignments(IDao<EmployeTache> employeTacheDao,
                                                      IEmployeDao employeDao,
                                                      ITacheDao tacheDao) {
        EmployeTache assignation1 = new EmployeTache();
        assignation1.setDateDebutReelle(new Date());
        assignation1.setDateFinReelle(new Date());
        assignation1.setEmploye(employeDao.findById(2));
        assignation1.setTache(tacheDao.findById(1));
        employeTacheDao.create(assignation1);

        EmployeTache assignation2 = new EmployeTache();
        assignation2.setDateDebutReelle(new Date());
        assignation2.setDateFinReelle(new Date());
        assignation2.setEmploye(employeDao.findById(3));
        assignation2.setTache(tacheDao.findById(2));
        employeTacheDao.create(assignation2);
    }

    private static void executeTestQueries(IEmployeDao employeDao, IProjetDao projetDao, ITacheDao tacheDao) {
        testTachesRealiseesParEmploye(employeDao);
        testProjetsGeresParEmploye(employeDao);
        testTachesPlanifieesParProjet(projetDao);
        testTachesRealiseesParProjet(projetDao);
        testTachesPrixSuperieur1000(tacheDao);
        testTachesEntreDates(tacheDao);
        affichageDetailleProjet(projetDao);
    }

    private static void testTachesRealiseesParEmploye(IEmployeDao employeDao) {
        Employe employeRecherche = employeDao.findById(2);
        List<Tache> listeTaches = employeDao.findTachesRealiseesByEmploye(employeRecherche.getId());

        System.out.println("==== Taches Realisees par " + employeRecherche.getNom() + " ====");
        if (listeTaches.isEmpty()) {
            System.out.println("Aucune tâche trouvée");
        } else {
            for (Tache tache : listeTaches) {
                System.out.println(tache.getNom());
            }
        }
    }

    private static void testProjetsGeresParEmploye(IEmployeDao employeDao) {
        Employe employeRecherche = employeDao.findById(1);
        List<Projet> listeProjets = employeDao.findProjetsGeresByEmploye(employeRecherche.getId());

        System.out.println("==== Projets Geres par " + employeRecherche.getNom() + " ====");
        if (listeProjets.isEmpty()) {
            System.out.println("Aucun projet trouvé");
        } else {
            for (Projet projet : listeProjets) {
                System.out.println(projet.getNom());
            }
        }
    }

    private static void testTachesPlanifieesParProjet(IProjetDao projetDao) {
        Projet projetRecherche = projetDao.findById(1);
        System.out.println("==== Taches Planifiees par " + projetRecherche.getNom() + " ====");

        List<Tache> listeTaches = projetDao.findTachesPlanifieesByProjet(projetRecherche.getId());
        if (listeTaches.isEmpty()) {
            System.out.println("Aucune tâche planifiée trouvée");
        } else {
            for (Tache tache : listeTaches) {
                System.out.println(tache.getNom());
            }
        }
    }

    private static void testTachesRealiseesParProjet(IProjetDao projetDao) {
        Projet projetRecherche = projetDao.findById(1);
        System.out.println("==== Taches Realisees par " + projetRecherche.getNom() + " ====");

        List<Tache> listeTaches = projetDao.findTachesRealiseesByProjet(projetRecherche.getId());
        if (listeTaches.isEmpty()) {
            System.out.println("Aucune tâche réalisée trouvée");
        } else {
            for (Tache tache : listeTaches) {
                System.out.println(tache.getNom());
            }
        }
    }

    private static void testTachesPrixSuperieur1000(ITacheDao tacheDao) {
        System.out.println("==== Prix Des Taches Superieur a 1000 ====");
        List<Tache> listeTaches = tacheDao.findTachesPrixSup1000();
        if (listeTaches.isEmpty()) {
            System.out.println("Aucune tâche avec prix > 1000 trouvée");
        } else {
            for (Tache tache : listeTaches) {
                System.out.println(tache.getNom());
            }
        }
    }

    private static void testTachesEntreDates(ITacheDao tacheDao) {
        System.out.println("==== Taches Entre Deux Dates ====");
        Date dateDebut = new Date(2024, 1, 1, 0, 0, 0);
        Date dateFin = new Date(2024, 12, 31, 0, 0, 0);

        List<Tache> listeTaches = tacheDao.findTachesRealiseesEntreDates(dateDebut, dateFin);
        if (listeTaches.isEmpty()) {
            System.out.println("Aucune tâche trouvée dans cette période");
        } else {
            for (Tache tache : listeTaches) {
                System.out.println(tache.getNom());
            }
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
        if (tachesProjet.isEmpty()) {
            System.out.println("Aucune tâche trouvée pour ce projet");
        } else {
            for (Tache tache : tachesProjet) {
                System.out.println(tache.getId() + " \t " + tache.getNom() +
                        " \t " + tache.getDateDebut() + " \t " + tache.getDateFin());
            }
        }
    }
}