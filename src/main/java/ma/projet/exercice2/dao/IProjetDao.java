package ma.projet.exercice2.dao;


import ma.projet.exercice2.classes.Projet;
import ma.projet.exercice2.classes.Tache;

import java.util.List;

public interface IProjetDao extends IDao<Projet> {
    public List<Tache> findTachesPlanifieesByProjet(int idProjet);
    public List<Tache> findTachesRealiseesByProjet(int idProjet);
}
