package g_contact.bll;

import g_contact.bo.Contact;
import g_contact.bo.Group;
import g_contact.data.DataBaseException;
import g_contact.data.GroupDao;
import org.apache.log4j.Logger;

import java.util.List;

public class GroupManager {
    private static Logger LOGGER = Logger.getLogger(GroupManager.class);
    private GroupDao groupDao = new GroupDao();

    public void addGroup(Group group, Contact contact) throws DataBaseException, Exception {
        // we check if the contact already exists in the group
        Group contactInGroup = groupDao.ContactExists(contact);
        if(contactInGroup != null){
            System.err.println("The contact already exist in the group !");
        }
        else {
            // Else we create the contact in the group
            groupDao.creatGroup(group,contact);
        }

    }

    public List<Group> getAll() throws DataBaseException{
        return groupDao.getAll();
    }


}
