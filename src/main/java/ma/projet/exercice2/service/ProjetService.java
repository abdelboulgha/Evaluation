package ma.projet.exercice2.service;

import ma.projet.exercice2.classes.Projet;
import ma.projet.exercice2.classes.Tache;
import ma.projet.exercice2.dao.IProjetDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("projetService")
@Transactional
public class ProjetService implements IProjetDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Projet p) {
        sessionFactory.getCurrentSession().save(p);
        return true;
    }

    @Override
    public boolean delete(Projet p) {
        sessionFactory.getCurrentSession().delete(p);
        return true;
    }

    @Override
    public boolean update(Projet p) {
        sessionFactory.getCurrentSession().update(p);
        return true;
    }

    @Override
    public Projet findById(int id) {
        return sessionFactory.getCurrentSession().get(Projet.class, id);
    }

    @Override
    public List<Projet> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Projet", Projet.class)
                .list();
    }

    public List<Tache> findTachesPlanifieesByProjet(int idProjet) {
        String hql = """
            from Tache t
            where t.projet.id = :idProjet
            and t.dateDebut is not null
            and t.dateFin is not null
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Tache.class)
                .setParameter("idProjet", idProjet)
                .list();
    }

    public List<Tache> findTachesRealiseesByProjet(int idProjet) {
        String hql = """
            select distinct t
            from Tache t
            join t.employeTaches et
            where t.projet.id = :idProjet
            and et.dateFinReelle is not null
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Tache.class)
                .setParameter("idProjet", idProjet)
                .list();
    }


}
