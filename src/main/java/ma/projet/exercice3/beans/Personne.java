package ma.projet.exercice3.beans;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_personne")
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    private Date dateNaissance;

    public Personne() {
    }

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Personne(String nom, String prenom, String telephone, String adresse) {
        this(nom, prenom);
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public int getAge() {
        if (dateNaissance == null) {
            return 0;
        }
        LocalDate naissance = LocalDate.of(
                dateNaissance.getYear() + 1900,
                dateNaissance.getMonth() + 1,
                dateNaissance.getDate()
        );
        return LocalDate.now().getYear() - naissance.getYear();
    }

    public boolean estMajeur() {
        return getAge() >= 18;
    }

    public boolean estMineur() {
        return getAge() < 18;
    }

    public boolean aUnTelephone() {
        return telephone != null && !telephone.trim().isEmpty();
    }

    public boolean aUneAdresse() {
        return adresse != null && !adresse.trim().isEmpty();
    }

    public boolean estValide() {
        return nom != null && !nom.trim().isEmpty() &&
                prenom != null && !prenom.trim().isEmpty();
    }

    public void mettreAJourContact(String telephone, String adresse) {
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public String getInitiales() {
        if (nom == null || prenom == null) {
            return "";
        }
        return prenom.charAt(0) + nom.charAt(0) + "";
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", age=" + getAge() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Personne personne = (Personne) obj;
        return id == personne.id;
    }
}
