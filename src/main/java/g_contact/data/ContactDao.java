package g_contact.data;

import g_contact.bo.Contact;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements CRUD operations for the BO Theme
 */
public class ContactDao {
    // attributes
    private Connection c;
    private String query;
    private PreparedStatement pstm;


    private Logger looger = Logger.getLogger(getClass());
    public void create(Contact contact) throws DataBaseException {
        int generatedId =-1 ;

        try{
            // Get the connexion to database
            c = DbConnection.getInstance();

            // SQL request
            query = "insert into contact(nom,prenom,tele1,tele2,adresse,email_personnel,email_professionnel,genre)" +
                    "values(?,?,?,?,?,?,?,?)";

            pstm = c.prepareStatement(query);
            pstm.setString(1,contact.getNom());
            pstm.setString(2,contact.getPrenom());
            pstm.setString(3,contact.getTelephone1());
            pstm.setString(4,contact.getTelephone2());
            pstm.setString(5,contact.getAdresse());
            pstm.setString(6,contact.getPersonnel());
            pstm.setString(7,contact.getProfessional());
            pstm.setString(8,contact.getGenre());

            // execute the sql request
            pstm.executeUpdate();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
                contact.setId(generatedId);
            }

        }catch (SQLException ex){
            // tracing the error
            looger.error("Error caused by :",ex);
            // raise error
            throw new DataBaseException(ex);
        }

    }

    public List<Contact> getAll() throws DataBaseException {
        List<Contact> contactList = new ArrayList<>();
        try{
            // Get the connexion to database
            c = DbConnection.getInstance();
            query = "SELECT * FROM Contact ORDER BY nom,prenom";
            pstm = c.prepareStatement(query);
            ResultSet result = pstm.executeQuery();
            while(result.next()){
                contactList.add(resultToContact(result));
            }
            result.close();
        }catch (SQLException ex){
            // tracer l'erreur
            looger.error("Error caused by :", ex);
            // raise error
            throw new DataBaseException(ex);
        }


        return contactList;
    }

    private Contact resultToContact(ResultSet rs) throws SQLException{
        return new Contact(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("tele1"),rs.getString("tele2"),rs.getString("adresse"),rs.getString("Email_personnel"),rs.getString("Email_professionnel"),rs.getString("genre"));
    }

    public void delete(int id) throws DataBaseException {

        try {
            // Get the connexion to database
            c = DbConnection.getInstance();
            query ="DELETE FROM Contact WHERE id =?";
            pstm = c.prepareStatement(query);
            pstm.setInt(1,id);
            pstm.executeUpdate();
        }catch (SQLException ex){
            looger.error("Error caused by :", ex);
            throw new DataBaseException(ex);
        }
    }

    public void update(Contact contact,String fname, String lname) throws DataBaseException{

        try {
            c = DbConnection.getInstance();
            query = "UPDATE CONTACT SET nom=?,prenom=?,tele1=?,tele2=?,adresse=?,email_personnel=?,Email_professionnel=?," +
                    "genre=? WHERE nom =? or prenom =?";
            pstm = c.prepareStatement(query);
            pstm.setString(1,contact.getNom());
            pstm.setString(2,contact.getPrenom());
            pstm.setString(3,contact.getTelephone1());
            pstm.setString(4,contact.getTelephone2());
            pstm.setString(5,contact.getAdresse());
            pstm.setString(6,contact.getPersonnel());
            pstm.setString(7,contact.getProfessional());
            pstm.setString(8,contact.getGenre());
            pstm.setString(9,fname);
            pstm.setString(10,lname);

            pstm.executeUpdate();
        }catch (SQLException ex){
            looger.error("Error caused by: ", ex);
            throw new DataBaseException(ex);
        }
    }

    // Find contact with name
    public Contact findContactByName(String fname, String lname) throws DataBaseException {
        List<Contact> list = new ArrayList<>();
        try{
            c = DbConnection.getInstance();
            query = "SELECT * FROM Contact WHERE nom = ? and  prenom = ?";
            pstm = c.prepareStatement(query);
            pstm.setString(1,fname);
            pstm.setString(2,lname);
            ResultSet result = pstm.executeQuery();

            while (result.next()){
                list.add(resultToContact(result));
            }

            result.close();
        }catch (SQLException ex){
            // tracer l'erreur
            looger.error("Error caused by :",ex);

            // throw error
            throw new DataBaseException(ex);
        }
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    // Find contact by personal or professional phone number
    public Contact findContactByNumber(String pNumber) throws DataBaseException{
        List<Contact> list = new ArrayList<>();
        try{
            c = DbConnection.getInstance();
            query = "SELECT * FROM Contact WHERE tele1 = ? or  tele2 = ?";
            pstm = c.prepareStatement(query);
            pstm.setString(1,pNumber);
            pstm.setString(2,pNumber);
            ResultSet result = pstm.executeQuery();

            while (result.next()){
                list.add(resultToContact(result));
            }

            result.close();
        }catch (SQLException ex){
            // tracer l'erreur
            looger.error("Error caused by :",ex);

            // throw error
            throw new DataBaseException(ex);
        }
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);

    }

}

