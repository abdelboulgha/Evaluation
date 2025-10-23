package ma.projet.exercice3.service;

import ma.projet.exercice3.beans.Femme;
import ma.projet.exercice3.dao.IFemmeDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("femmeService")
@Transactional
public class FemmeService implements IFemmeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Femme f) {
        sessionFactory.getCurrentSession().save(f);
        return true;
    }

    @Override
    public boolean delete(Femme f) {
        sessionFactory.getCurrentSession().delete(f);
        return true;
    }

    @Override
    public boolean update(Femme f) {
        sessionFactory.getCurrentSession().update(f);
        return true;
    }

    @Override
    public Femme findById(int id) {
        return sessionFactory.getCurrentSession().get(Femme.class, id);
    }

    @Override
    public List<Femme> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Femme", Femme.class)
                .list();
    }

    @Override
    public int countEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        Object result =  sessionFactory.getCurrentSession()
                .createNamedQuery("Femme.countEnfantsEntreDates")
                .setParameter("femmeId", femmeId)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

    @Override
    public List<Femme> findFemmesMarieesDeuxFois() {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("Femme.findMarieesDeuxFois", Femme.class)
                .list();
    }

    @Override
    public Femme findFemmeLaPlusAgee() {
        String hql = """
            from Femme f
            where f.dateNaissance = (
                select min(f2.dateNaissance) from Femme f2
            )
        """;
        List<Femme> results = sessionFactory.getCurrentSession()
                .createQuery(hql, Femme.class)
                .setMaxResults(1)
                .list();

        return results.isEmpty() ? null : results.get(0);
    }
}
