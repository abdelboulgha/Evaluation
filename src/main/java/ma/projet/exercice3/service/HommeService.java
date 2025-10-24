package ma.projet.exercice3.service;

import ma.projet.exercice3.beans.Femme;
import ma.projet.exercice3.beans.Homme;
import ma.projet.exercice3.dao.IHommeDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("hommeService")
@Transactional
public class HommeService implements IHommeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Homme h) {
        sessionFactory.getCurrentSession().save(h);
        return true;
    }

    @Override
    public boolean delete(Homme h) {
        sessionFactory.getCurrentSession().delete(h);
        return true;
    }

    @Override
    public boolean update(Homme h) {
        sessionFactory.getCurrentSession().update(h);
        return true;
    }

    @Override
    public Homme findById(int id) {
        return sessionFactory.getCurrentSession().get(Homme.class, id);
    }

    @Override
    public List<Homme> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Homme", Homme.class)
                .list();
    }

    @Override
    public List<Femme> findEpouses(int hommeId) {
        String hql = """
            select f
            from Mariage m
            join m.femme f
            where m.homme.id = :hommeId
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Femme.class)
                .setParameter("hommeId", hommeId)
                .list();
    }

    @Override
    public List<Object[]> findMariagesDetails(int hommeId) {
        String hql = """
            select f.nom, f.prenom, m.dateDebut, m.nbrEnfant
            from Mariage m
            join m.femme f
            where m.homme.id = :hommeId
            and m.dateFin IS NULL
            order by m.dateDebut
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Object[].class)
                .setParameter("hommeId", hommeId)
                .list();
    }

    @Override
    public List<Object[]> findMariagesEchoues(int hommeId) {
        String hql = """
            select f.nom, f.prenom, m.dateDebut, m.dateFin, m.nbrEnfant
            from Mariage m
            join m.femme f
            where m.homme.id = :hommeId
            and m.dateFin IS NOT NULL 
            order by m.dateDebut
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Object[].class)
                .setParameter("hommeId", hommeId)
                .list();
    }

    @Override
    public List<Homme> countHommesMarieQuatreFemmesEntreDates(Date dateDebut, Date dateFin) {
        String hql = """
        select h
        from Homme h
        join h.mariages m
        where m.dateFin IS NULL
        group by h
        having count(m) = 4
    """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Homme.class)
                .list();
    }
}