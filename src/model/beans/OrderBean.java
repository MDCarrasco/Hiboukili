package model.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.sql.DataSource;
import model.classes.Commande;

public class OrderBean implements Serializable {

    //HashMap<String, Commande>map;
    ArrayList commandes;

    public OrderBean() {
        //this.map=new HashMap();
        this.commandes = new ArrayList();

    }

    public void create(ShoppingCartBean cart, ConnexionBean bc) {
        //methode pour afficher les info general de la commande a partir du panier de l'utilisateur

        cart.getCartPrice();
        //cart.qtyTotalCart();
        recupererStatutCommande(bc);

    }

    public void create() {
        /* Cree la commande avec  : 
         - adresselivraison
         - adressefactuation
         -utilisateur
         -numero commande
         - date commande*/
    }

    public void save(ConnexionBean bc) {
        int i = 0;

        DataSource ds = bc.MaConnexion();

        try (Connection c = ds.getConnection();) {

        //requete SQL pour sauvegarder la commande dans la base de donnée.
            //requete de base.
//        String query ="DECLARE @guid varchar(50);"
//                       + " SET @guid= NEWID();"
//                       + " INSERT INTO Commande(idAdresseFacturation, idAdresseLivraison, idUtilisateur, numero, dateCommande)"
//                       + " SELECT '1','2',a.idUtilisateur,@guid, '2016-02-05'"
//                       + " FROM Utilisateur AS a"
//                       + " INNER JOIN   DernieresFacturations AS b"
//                       + " ON a.IdUtilisateur=b.IdUtilisateur"
//                       + " INNER JOIN DernieresLivraisons AS c"
//                       + " ON a.IdUtilisateur=c.idUtilisateur"
//                       + " WHERE a.nom='PetitJean'";
            //requete adapté à la situation 
            String query = "DECLARE @guid varchar(50);"
                    + " SET @guid= NEWID();"
                    + " INSERT INTO Commande(idAdresseFacturation, idAdresseLivraison, idUtilisateur, numero, dateCommande)"
                    + " SELECT ?,?,?,@guid, ?"
                    + " FROM Utilisateur AS a"
                    + " INNER JOIN   DernieresFacturations AS b"
                    + " ON a.IdUtilisateur=b.IdUtilisateur"
                    + " INNER JOIN DernieresLivraisons AS c"
                    + " ON a.IdUtilisateur=c.idUtilisateur";
            PreparedStatement stmt = c.prepareStatement(query);
           /* stmt.setLong(1, valeur de la combo);
            stmt.setLong(2, valeur de la combo);
            stmt.setLong(3, cookie "sync");
            stmt.setDate(4, valeur date demandé depuis controller);*/
            
            

            stmt.close();

        } catch (SQLException ex) {
            System.err.println("Erreur dans Statut commande" + ex.getMessage());
        }

        ds = bc.MaConnexion();

        //recuperer la hasmap commande 
        //appel de la methode create 2 
        //Insert into commande
        //sauvegarder la commande en base de donnée : 
        //pour cela il faut add la map avec : 
        // - idAdresseLivraison ( choix de l'utilisateur dans la combo) 
        // - idAdresseFacturation ( la meme)
        // - id Utilisateur ( recuperer le login user )
        // - numero commande ( gestion sql newid()
        // - dateCommande ( demaner au controller de recuperer la date courante)
        //envoyer tout ca Insert into commande.
    }

    public void modifier() {
        // modifier et supprimer : 
        // retour au panier
    }

    public String recupererStatutCommande(ConnexionBean bc) {
        int i = 0;
        String statCommande = null;

        DataSource ds = bc.MaConnexion();

        try (Connection c = ds.getConnection();) {

            String query = "SELECT libelle FROM StatutCommande WHERE code = 'cp1'";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                statCommande = (rs.getString("libelle"));
            }
            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.err.println("Erreur dans Statut commande" + ex.getMessage());
        }

        ds = bc.MaConnexion();

        return statCommande;

    }

    public void recupererUtilisateur() {
        //pas necessaire de faire une methode : juste recuperre le cookie sync pour recuperer l'id utilisateur.
    }

    public Collection<Commande> list() {
        return commandes;
    }

    public int size() {
        return commandes.size();
    }

    public boolean isEmpty() {
        return commandes.isEmpty();
    }

    public void clean() {
        commandes.clear();
        //apres la validation final : clean map
    }

}
