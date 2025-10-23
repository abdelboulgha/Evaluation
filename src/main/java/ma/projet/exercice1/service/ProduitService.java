package ma.projet.exercice1.service;

import jakarta.transaction.Transactional;
import ma.projet.exercice1.classes.Produit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ProduitService {

    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public boolean create(Produit Produit) {
        Session session = sessionFactory.getCurrentSession();
        session.save(Produit);
        return true;
    }


    public boolean delete(Produit Produit) {
        sessionFactory.getCurrentSession().delete(Produit);
        return true;
    }


    public boolean update(Produit Produit) {
        sessionFactory.getCurrentSession().update(Produit);
        return true;
    }


    public Produit findById(int id) {
        return sessionFactory.getCurrentSession().get(Produit.class, id);
    }


    public List<Produit> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Produit", Produit.class)
                .list();
    }


    public List<Produit> findProduitsByCategorie(int idCategorie) {
        String hql = "from Produit p where p.categorie.id = :idCat";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Produit.class)
                .setParameter("idCat", idCategorie)
                .list();
    }


    public List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        String hql = """
            select distinct p
            from Produit p
            join p.lignes l
            join l.commande c
            where c.date between :d1 and :d2
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Produit.class)
                .setParameter("d1", dateDebut)
                .setParameter("d2", dateFin)
                .list();
    }


    public List<Produit> findProduitsByCommande(int idCommande) {
        String hql = """
            select p
            from Produit p
            join p.lignes l
            join l.commande c
            where c.id = :idCmd
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Produit.class)
                .setParameter("idCmd", idCommande)
                .list();
    }


    public int findQuantiteOfProduitsInLigne(int idProduit) {
        String hql = """
            select l.quantite
            from LigneCommande l
            where l.produit.id = :idProduit
        """;
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Integer.class)
                .setParameter("idProduit", idProduit)
                .uniqueResult();
    }


    public List<Produit> findProduitsPrixSup100() {
        return sessionFactory.getCurrentSession()
                .createNamedQuery("Produit.findPrixSup100", Produit.class)
                .list();
    }
}
