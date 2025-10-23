package ma.projet.exercice1.service;

import jakarta.transaction.Transactional;
import ma.projet.exercice1.classes.Categorie;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CategorieService {

    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public boolean create(Categorie Categorie) {
        sessionFactory.getCurrentSession().save(Categorie);
        return true;
    }


    public boolean delete(Categorie Categorie) {
        sessionFactory.getCurrentSession().delete(Categorie);
        return true;
    }


    public boolean update(Categorie Categorie) {
        sessionFactory.getCurrentSession().update(Categorie);
        return true;
    }


    public Categorie findById(int id) {
        return sessionFactory.getCurrentSession().get(Categorie.class, id);
    }


    public List<Categorie> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Categorie", Categorie.class).list();
    }
}
