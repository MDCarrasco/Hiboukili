package model.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import model.classes.StatutUtilisateur;
import model.classes.Utilisateur;

public class BeanLogin implements Serializable {

    public BeanLogin() {

    }

    public Utilisateur checkLogin(DataSource ds, BeanConnexion bc, String login, String mdp) {

        ds = bc.MaConnexion();//connexion
        try (Connection c = ds.getConnection()) {
            //requete
            //String query = "select mail, mot_de_passe from Utilisateur where mail = ? and mot_de_passe = ?";
            String query = "select u.idStatutUtilisateur, "
                    + "nom, "
                    + "prenom, "
                    + "date_naissance, "
                    + "telephone, "
                    + "mail, "
                    + "mot_de_passe, "
                    + "libelle, "
                    + "code "
                    + "from Utilisateur as u "
                    + "join StatutUtilisateur as su "
                    + "on u.idStatutUtilisateur = su.idStatutUtilisateur "
                    + "where  mail = ? and mot_de_passe = ?";

            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, login);//renvoie la valeur du login saisi pour where mail = ?
            ps.setString(2, mdp);//renvoie la valeur du mdp saisi pour where mot_de_passe = ?

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {//s'il existe un couple login/mdp existant ds la BDD
                StatutUtilisateur su = new StatutUtilisateur();

                su.setId(rs.getLong("idStatutUtilisateur"));
                su.setLibelle(rs.getString("libelle"));
                su.setCode(rs.getString("code"));
                

                Utilisateur u = new Utilisateur();
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setDateNaissance(rs.getDate("date_naissance"));
                u.setTelephone(rs.getString("telephone"));
                u.setEmail(rs.getString("mail"));
                u.setMotDePasse(rs.getString("mot_de_passe"));
                
                u.setStatut(su);

                System.out.println(u.getNom() + " " + u.getPrenom() + " " + u.getStatut().getCode());
                return u; //renvoie vrai
            }

        } catch (SQLException ex) {
            System.err.println("Erreur dans BeanLogin : " + ex.getMessage() + "\n");
        }

        return null;//sinon renvoie faux
    }
}
