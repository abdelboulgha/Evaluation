package ma.projet.exercice1.service;

import ma.projet.exercice1.classes.Categorie;
import ma.projet.exercice1.dao.IDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CategorieService implements IDao<Categorie> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @org.springframework.transaction.annotation.Transactional
    public boolean create(Categorie Categorie) {
        sessionFactory.getCurrentSession().save(Categorie);
        return true;
    }

    @Override
    public boolean delete(Categorie Categorie) {
        sessionFactory.getCurrentSession().delete(Categorie);
        return true;
    }

    @Override
    public boolean update(Categorie Categorie) {
        sessionFactory.getCurrentSession().update(Categorie);
        return true;
    }

    @Override
    public Categorie findById(int id) {
        return sessionFactory.getCurrentSession().get(Categorie.class, id);
    }

    @Override
    public List<Categorie> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Categorie", Categorie.class).list();
    }
}
