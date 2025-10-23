package ma.projet.exercice3.service;

import ma.projet.exercice3.beans.Mariage;
import ma.projet.exercice3.dao.IDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("mariageService")
@Transactional
public class MariageService implements IDao<Mariage> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Mariage m) {
        sessionFactory.getCurrentSession().save(m);
        return true;
    }

    @Override
    public boolean delete(Mariage m) {
        sessionFactory.getCurrentSession().delete(m);
        return true;
    }

    @Override
    public boolean update(Mariage m) {
        sessionFactory.getCurrentSession().update(m);
        return true;
    }

    @Override
    public Mariage findById(int id) {
        return sessionFactory.getCurrentSession().get(Mariage.class, id);
    }

    @Override
    public List<Mariage> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Mariage", Mariage.class)
                .list();
    }
}
