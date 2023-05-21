package g_contact.ihm;

import g_contact.bll.ContactManager;
import g_contact.bo.Contact;
import g_contact.data.DataBaseException;
import g_contact.data.DataBaseInstaller;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;


public class Main {

    private static final Logger logger  = Logger.getLogger(Main.class);
    static void mainMenu(){
        System.out.println("******************* Main Menu ******************");
        System.out.println("1. create new contact");
        System.out.println("2. Show the contact list in alphabetic order");
        System.out.println("3. delete a contact");
        System.out.println("4. Update a contact");
        System.out.println("5. Search for contact by name");
        System.out.println("0. Exit");
    }
    public static void main(String[] args) throws Exception {
        // create a ContactManager instance
        ContactManager contactManager = new ContactManager();

        try {
            if (!DataBaseInstaller.checkIfAlreadyInstalled()){
                DataBaseInstaller.run();
                logger.info("Database created correctly");
            }
        }catch (Exception ex){
            // We raise an error message if the tables was not created successfully
            System.err.println("Error while creating the database, see log.txt file for details");
            // we stop the application with an error code
            System.exit(-1);
        }
        //To get data from the keyboard
        Scanner sc = new Scanner(System.in);
        while (true){
            // Display Main Menu
            mainMenu();
            //
            System.out.println("Enter your choice :");
            int choice = sc.nextInt();
            sc.nextLine(); // to avoid jump by nexInt
            switch (choice){
                case 1:
                    System.out.println("Enter the contact first name: ");
                    // get the name
                    String fname = sc.nextLine();
                    System.out.println("Enter the contact last name: ");
                    String lname = sc.nextLine();
                    // Checks if the contact already exist to raise an error message if it is already exist
                    if(View.isContactExist(contactManager,fname,lname)){
                        break;
                    }
                    System.out.println("Enter the contact phone1: ");
                    String phone1 = sc.nextLine();
                    System.out.println("Enter the contact phone2: ");
                    String phone2 = sc.nextLine();
                    System.out.println("Enter the contact address: ");
                    String address = sc.nextLine();
                    System.out.println("Enter the contact personnel email: ");
                    String Email_per = sc.nextLine();
                    System.out.println("Enter the contact professional email: ");
                    String Email_pro = sc.nextLine();
                    System.out.println("Enter the contact gender: ");
                    String gender = sc.nextLine();
                    try {
                        // We build the contact object then save it in the dataBase
                        contactManager.addContact(new Contact(fname,lname,phone1,phone2
                                ,address,Email_per,Email_pro,gender));
                        System.out.println("The contact added successfully");
                        // We display the contact list
                        List<Contact> contactList = contactManager.getAll();
                        for (Contact contact:contactList){
                            System.out.println("First name: "+contact.getNom()+"-- Last name: "+contact.getPrenom()+"-- Phone1: "+contact.getTelephone1());

                        }
                    }catch (DataBaseException ex){
                        // show an error if there is Db trouble
                        System.err.println(ex.getMessage());
                    }
                    break;

                case 2:
                    // we Call a static method in view class to show all data in the database
                    View.showData(contactManager);
                    break;
                case 3:
                    try{
                        System.out.println("Enter the contact id: ");
                        // get the contact id
                        int id_del = sc.nextInt();
                        sc.nextLine();
                        contactManager.delete(id_del);
                        System.out.println("The contact with the id:"+id_del+" has been deleted for the data base");

                    }catch (DataBaseException ex){
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 4:
                    View.showData(contactManager);
                    try {
                        System.out.println("Enter the contact first name: ");
                        String Ufname0 = sc.nextLine();
                        System.out.println("Enter the contact last name: ");
                        String Ulname0 = sc.nextLine();
                        Contact uContact = contactManager.findContactByName(Ufname0, Ulname0);

                        if(uContact != null){
                            System.out.println("Enter the new first name: ");
                            String fname1 = sc.nextLine();
                            System.out.println("Enter the new last name: ");
                            String lname1 = sc.nextLine();
                            System.out.println("Enter the new phone1: ");
                            String phone11 = sc.nextLine();
                            System.out.println("Enter the new phone2: ");
                            String phone21 = sc.nextLine();
                            System.out.println("Enter the new address: ");
                            String address1 = sc.nextLine();
                            System.out.println("Enter the new personnel email: ");
                            String Email_per1 = sc.nextLine();
                            System.out.println("Enter the new professional email: ");
                            String Email_pro1 = sc.nextLine();
                            System.out.println("Enter the contact gender: ");
                            String gender1 = sc.nextLine();
                            uContact.setAll(fname1,lname1,phone11,phone21,address1,Email_per1,Email_pro1,gender1);
                            contactManager.update(uContact, Ufname0, Ulname0);
                        }else {
                            System.err.println("The contact does not exist !");
                        }

                    }catch (DataBaseException ex){
                        System.out.println(ex.getMessage());
                    }

                    break;
                case 5:
                    break;
                case 0:
                    System.exit(-1);

            }

        }


    }
}