package ma.projet.exercice1.service;

import ma.projet.exercice1.classes.LigneCommande;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LigneCommandeService {

    @Autowired
    private SessionFactory sessionFactory;


    public boolean create(LigneCommande c) {
        sessionFactory.getCurrentSession().save(c);
        return true;
    }


    public boolean delete(LigneCommande c) {
        sessionFactory.getCurrentSession().delete(c);
        return true;
    }


    public boolean update(LigneCommande c) {
        sessionFactory.getCurrentSession().update(c);
        return true;
    }


    public LigneCommande findById(int id) {
        return sessionFactory.getCurrentSession().get(LigneCommande.class, id);
    }


    public List<LigneCommande> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from LigneCommande", LigneCommande.class)
                .list();
    }
}
