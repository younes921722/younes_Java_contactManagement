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

    public void deleteGroup(String  groupName) throws DataBaseException {
        try {
            // Get the connexion to database
            c = DbConnection.getInstance();
            query ="DELETE FROM `group` WHERE group_name =? ";
            pstm = c.prepareStatement(query);
            pstm.setString(1,groupName);
            pstm.executeUpdate();
        }catch (SQLException | DataBaseException ex){
            logger.error("Error caused by :", ex);
            throw new DataBaseException(ex);
        }
    }
    private Group resultToGroup(ResultSet rs) throws SQLException{
        return new Group(rs.getString("group_name"),rs.getInt(2));
    }

    // It checks if the group exists
    public Group isGroupExists(Group group) throws DataBaseException{
        List<Group> grouptList = new ArrayList<>();
        try {
            c = DbConnection.getInstance();
            query = "SELECT * FROM `group` WHERE group_name=?";
            pstm = c.prepareStatement(query);
            pstm.setString(1,group.getName());
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

    public Group findGroupByName(String gName) throws DataBaseException{
        List<Group> list = new ArrayList<>();
        try {
            // Get the connexion to database
            c = DbConnection.getInstance();
            query ="SELECT * FROM `Group` WHERE group_name =? ";
            pstm = c.prepareStatement(query);
            pstm.setString(1,gName);
            ResultSet result = pstm.executeQuery();
            while (result.next()){
                list.add(resultToGroup(result));
            }
            result.close();
        }catch (SQLException | DataBaseException ex){
            logger.error("Error caused by :", ex);
            throw new DataBaseException(ex);
        }if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    // check if the contact exists in the group
    public Group ContactExists(Contact contact, Group group) throws DataBaseException{
        List<Group> grouptList = new ArrayList<>();
        try {
            c = DbConnection.getInstance();
            query = "SELECT * FROM `group` WHERE contact_id=? and group_name = ? ";
            pstm = c.prepareStatement(query);
            pstm.setInt(1,contact.getId());
            pstm.setString(2,group.getName());
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
