package ma.projet.exercice1.service;

import ma.projet.exercice1.classes.LigneCommande;
import ma.projet.exercice1.dao.IDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class LigneCommandeService implements IDao<LigneCommande> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(LigneCommande c) {
        sessionFactory.getCurrentSession().save(c);
        return true;
    }

    @Override
    public boolean delete(LigneCommande c) {
        sessionFactory.getCurrentSession().delete(c);
        return true;
    }

    @Override
    public boolean update(LigneCommande c) {
        sessionFactory.getCurrentSession().update(c);
        return true;
    }

    @Override
    public LigneCommande findById(int id) {
        return sessionFactory.getCurrentSession().get(LigneCommande.class, id);
    }

    @Override
    public List<LigneCommande> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from LigneCommande", LigneCommande.class)
                .list();
    }
}
