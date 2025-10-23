package ma.projet.exercice2.service;

import ma.projet.exercice2.dao.IDao;
import ma.projet.exercice2.classes.EmployeTache;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("employeTacheService")
@Transactional
public class EmployeTacheService implements IDao<EmployeTache> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(EmployeTache et) {
        sessionFactory.getCurrentSession().save(et);
        return true;
    }

    @Override
    public boolean delete(EmployeTache et) {
        sessionFactory.getCurrentSession().delete(et);
        return true;
    }

    @Override
    public boolean update(EmployeTache et) {
        sessionFactory.getCurrentSession().update(et);
        return true;
    }

    @Override
    public EmployeTache findById(int id) {
        return sessionFactory.getCurrentSession().get(EmployeTache.class, id);
    }

    @Override
    public List<EmployeTache> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from EmployeTache", EmployeTache.class)
                .list();
    }

    // Example: find assignments by employee
    public List<EmployeTache> findByEmployeId(int idEmploye) {
        String hql = "from EmployeTache et where et.employe.id = :idEmploye";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, EmployeTache.class)
                .setParameter("idEmploye", idEmploye)
                .list();
    }

    // Example: find assignments by task
    public List<EmployeTache> findByTacheId(int idTache) {
        String hql = "from EmployeTache et where et.tache.id = :idTache";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, EmployeTache.class)
                .setParameter("idTache", idTache)
                .list();
    }
}
