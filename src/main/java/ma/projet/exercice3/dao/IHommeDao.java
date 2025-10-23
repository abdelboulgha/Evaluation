package ma.projet.exercice3.dao;

import ma.projet.exercice3.beans.Femme;
import ma.projet.exercice3.beans.Homme;

import java.util.Date;
import java.util.List;

public interface IHommeDao extends IDao<Homme> {
    List<Femme> findEpouses(int hommeId);
    List<Object[]> findMariagesDetails(int hommeId);
    List<Object[]> findMariagesEchoues(int hommeId);
    List<Homme> countHommesMarieQuatreFemmesEntreDates(Date dateDebut, Date dateFin);
}
