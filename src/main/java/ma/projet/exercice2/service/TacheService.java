package ma.projet.exercice2.service;
import ma.projet.exercice2.classes.Tache;
import ma.projet.exercice2.dao.ITacheDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("tacheService")
@Transactional
public class TacheService implements ITacheDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Tache t) {
        sessionFactory.getCurrentSession().save(t);
        return true;
    }

    @Override
    public boolean delete(Tache t) {
        sessionFactory.getCurrentSession().delete(t);
        return true;
    }

    @Override
    public boolean update(Tache t) {
        sessionFactory.getCurrentSession().update(t);
        return true;
    }

    @Override
    public Tache findById(int id) {
        return sessionFactory.getCurrentSession().get(Tache.class, id);
    }

    @Override
    public List<Tache> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Tache", Tache.class)
                .list();
    }

    public List<Tache> findTachesPrixSup1000() {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("Tache.findPrixSup1000", Tache.class)
                .list();
    }

    public List<Tache> findTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        String hql = """
            select distinct t
            from Tache t
            join t.employeTaches et
            where et.dateFinReelle between :d1 and :d2
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Tache.class)
                .setParameter("d1", dateDebut)
                .setParameter("d2", dateFin)
                .list();
    }
}

