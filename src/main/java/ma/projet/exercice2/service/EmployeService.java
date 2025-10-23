package ma.projet.exercice2.service;

import ma.projet.exercice2.classes.Employe;
import ma.projet.exercice2.classes.Projet;
import ma.projet.exercice2.classes.Tache;
import ma.projet.exercice2.dao.IEmployeDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("employeServcie")
@Transactional
public class EmployeService implements IEmployeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Employe e) {
        sessionFactory.getCurrentSession().save(e);
        return true;
    }

    @Override
    public boolean delete(Employe e) {
        sessionFactory.getCurrentSession().delete(e);
        return true;
    }

    @Override
    public boolean update(Employe e) {
        sessionFactory.getCurrentSession().update(e);
        return true;
    }

    @Override
    public Employe findById(int id) {
        return sessionFactory.getCurrentSession().get(Employe.class, id);
    }

    @Override
    public List<Employe> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Employe", Employe.class)
                .list();
    }

    public List<Tache> findTachesRealiseesByEmploye(int idEmploye) {
        String hql = """
            select distinct t
            from Tache t
            join t.employeTaches et
            where et.employe.id = :idEmploye
            and et.dateFinReelle is not null
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Tache.class)
                .setParameter("idEmploye", idEmploye)
                .list();
    }

    public List<Projet> findProjetsGeresByEmploye(int idEmploye) {
        String hql = "from Projet p where p.chef.id = :idEmploye";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Projet.class)
                .setParameter("idEmploye", idEmploye)
                .list();
    }
}
