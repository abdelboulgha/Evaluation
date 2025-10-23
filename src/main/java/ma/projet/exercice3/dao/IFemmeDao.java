package ma.projet.exercice3.dao;


import ma.projet.exercice3.beans.Femme;

import java.util.Date;
import java.util.List;

public interface IFemmeDao extends IDao<Femme> {
    int countEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin);
    List<Femme> findFemmesMarieesDeuxFois();
    public Femme findFemmeLaPlusAgee();
}
