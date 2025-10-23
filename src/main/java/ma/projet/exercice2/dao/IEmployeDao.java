package ma.projet.exercice2.dao;

import ma.projet.exercice2.classes.Employe;
import ma.projet.exercice2.classes.Projet;
import ma.projet.exercice2.classes.Tache;

import java.util.List;

public interface IEmployeDao extends IDao<Employe> {
    List<Tache> findTachesRealiseesByEmploye(int idEmploye);
    List<Projet> findProjetsGeresByEmploye(int idEmploye);
}
