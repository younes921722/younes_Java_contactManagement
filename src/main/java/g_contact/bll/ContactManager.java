package g_contact.bll;

import g_contact.bo.Contact;
import g_contact.data.ContactDao;
import g_contact.data.DataBaseException;
import g_contact.utils.ExcelExporter;
import org.apache.log4j.Logger;

import java.util.List;

public class ContactManager {
    private static Logger LOGGER = Logger.getLogger(ContactManager.class);

    private ContactDao contactDao = new ContactDao();

    /**
     * Ajouter un contact
     */

    public void addContact(Contact pcontact) throws DataBaseException, BusinessLogicException{
        // We check if the contact already exists
        Contact contact = contactDao.findContactByName(pcontact.getNom(),pcontact.getPrenom());
        // if the contact already exist we throw an exception
        if (contact != null){
            throw new BusinessLogicException("The contact already exist !");
        }
        // Else we create the contact
        contactDao.create(pcontact);
    }

    public  Contact findContactByName(String fname, String lname) throws DataBaseException{
        Contact contact = contactDao.findContactByName(fname,lname);
        return contact;

    }

    public List<Contact> getAll() throws DataBaseException {
        return contactDao.getAll();
    }

    public void export() throws ExportException{
        try{
            List<Contact> contactList = contactDao.getAll();
            String[][] data = new String[contactList.size()][];

            int i = 0;
            for(Contact c:contactList){
                String[] excelRow = new String[]{String.valueOf(c.getId()), c.getNom(), c.getPrenom(), c.getTelephone1(), c.getTelephone2(), c.getAdresse(), c.getPersonnel(), c.getProfessional(), c.getGenre()};
                data[i++] = excelRow;
            }
            ExcelExporter exporter = new ExcelExporter(new String[]{"ISBN", "Titre", "Auteur", "Thème", "Quantité"}, data, "livres");
            exporter.export(System.getProperty("user.home")+"\\livres.xlsx");
        }catch (Exception ex){
            //tracing the error
            LOGGER.error(ex);
            throw new ExportException("Erreur d'export excel", ex);
        }
    }

    public void delete(int id) throws DataBaseException{
        ContactDao contactDao = new ContactDao();
        contactDao.delete(id);
    }
    public void update(Contact contact, String fname, String lname) throws DataBaseException{
        ContactDao contactDao1 = new ContactDao();
        contactDao1.update(contact, fname, lname);
    }

}
