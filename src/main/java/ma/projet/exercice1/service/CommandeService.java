package ma.projet.exercice1.service;

import jakarta.transaction.Transactional;
import ma.projet.exercice1.classes.Commande;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CommandeService {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean create(Commande c) {
        sessionFactory.getCurrentSession().save(c);
        return true;
    }


    public boolean delete(Commande c) {
        sessionFactory.getCurrentSession().delete(c);
        return true;
    }


    public boolean update(Commande c) {
        sessionFactory.getCurrentSession().update(c);
        return true;
    }


    public Commande findById(int id) {
        return sessionFactory.getCurrentSession().get(Commande.class, id);
    }


    public List<Commande> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Commande", Commande.class)
                .list();
    }
}
