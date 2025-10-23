package ma.projet.exercice1.service;

import jakarta.transaction.Transactional;
import ma.projet.exercice1.classes.Commande;
import ma.projet.exercice1.dao.IDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CommandeService implements IDao<Commande> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Commande c) {
        sessionFactory.getCurrentSession().save(c);
        return true;
    }

    @Override
    public boolean delete(Commande c) {
        sessionFactory.getCurrentSession().delete(c);
        return true;
    }

    @Override
    public boolean update(Commande c) {
        sessionFactory.getCurrentSession().update(c);
        return true;
    }

    @Override
    public Commande findById(int id) {
        return sessionFactory.getCurrentSession().get(Commande.class, id);
    }

    @Override
    public List<Commande> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Commande", Commande.class)
                .list();
    }
}
