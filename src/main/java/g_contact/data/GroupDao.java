package g_contact.data;

import g_contact.bo.Contact;
import g_contact.bo.Group;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GroupDao {
    private Connection c;
    private String query;
    private PreparedStatement pstm;
    private Logger logger = Logger.getLogger(GroupDao.class);
//    private List<Contact> addToGroup(int contact_id) throws DataBaseException {
//        List<Contact> contactList = new ArrayList<>();
//        try{
//            // Get the connexion to database
//            c = DbConnection.getInstance();
//            query = "SELECT * FROM Contact WHERE id = ?";
//            pstm = c.prepareStatement(query);
//            pstm.setInt(1,contact_id);
//            ResultSet result = pstm.executeQuery();
//            while(result.next()){
//                contactList.add(resultToContact(result));
//            }
//            result.close();
//        }catch (SQLException ex){
//            // tracer l'erreur
//            logger.error("Error caused by :", ex);
//            // raise error
//            throw new DataBaseException(ex);
//        }
//        return contactList;
//    }

    public void creatGroup(Group group,Contact contact) throws DataBaseException, SQLException {
        try{
            // Get the connexion to database
            c = DbConnection.getInstance();

            // SQL request
            query = "insert into `group` " +
                    "values(?,?)";
            pstm = c.prepareStatement(query);
            pstm.setString(1,group.getName());
            pstm.setInt(2,contact.getId());
            // execute the sql request
            pstm.executeUpdate();

        }catch (SQLException ex){
            // tracing the error
            logger.error("Error caused by :",ex);

            // rollback
            c.rollback();

            // raise an error
            throw new DataBaseException(ex);
        }

    }
    private Group resultToGroup(ResultSet rs) throws SQLException{
        return new Group(rs.getString("group_name"),rs.getInt(2));
    }

    // check if the contact exists in the group
    public Group ContactExists(Contact contact) throws DataBaseException{
        List<Group> grouptList = new ArrayList<>();
        try {
            c = DbConnection.getInstance();
            query = "SELECT * FROM `group` WHERE contact_id=?";
            pstm = c.prepareStatement(query);
            pstm.setInt(1,contact.getId());
            ResultSet result = pstm.executeQuery();
            while (result.next()){
                grouptList.add(resultToGroup(result));
            }

            result.close();
        }catch (SQLException ex){
            // trace error
            logger.error(ex.getMessage());
            // raise an error
            throw new DataBaseException(ex);
        }if(grouptList.isEmpty()){
            return null;
        }
        return grouptList.get(0);
    }
    public List<Group> getAll() throws DataBaseException{
        List<Group> grouptList = new ArrayList<>();
        try{
            // Get the connexion to database
            c = DbConnection.getInstance();
            query = "SELECT * FROM `group`";
            pstm = c.prepareStatement(query);
            ResultSet result = pstm.executeQuery();
            while(result.next()){
                grouptList.add(resultToGroup(result));
            }
            result.close();
        }catch (SQLException ex){
            // tracer l'erreur
            logger.error("Error caused by :", ex);
            // raise error
            throw new DataBaseException(ex);
        }
        return grouptList;
    }



}
