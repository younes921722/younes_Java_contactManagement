package g_contact.ihm;

import g_contact.bll.ContactManager;
import g_contact.bo.Contact;
import g_contact.data.DataBaseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class View {

    public static void tableView(List<String> headers, List<List<String>> data) {
        // Calculate column widths
        int numColumns = headers.size();
        int[] columnWidths = new int[numColumns];
        for (int i = 0; i < numColumns; i++) {
            columnWidths[i] = headers.get(i).length();
        }
        for (List<String> row : data) {
            for (int i = 0; i < numColumns; i++) {
                int width = row.get(i).length();
                if (width > columnWidths[i]) {
                    columnWidths[i] = width;
                }
            }
        }

        // Display the table
        printHorizontalLine(columnWidths);
        printRow(headers, columnWidths);
        printHorizontalLine(columnWidths);
        for (List<String> row : data) {
            printRow(row, columnWidths);
        }
        printHorizontalLine(columnWidths);
    }

    private static void printHorizontalLine(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print("+");
            for (int i = 0; i < width + 2; i++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    private static void printRow(List<String> row, int[] columnWidths) {
        System.out.print("|");
        for (int i = 0; i < row.size(); i++) {
            String data = row.get(i);
            int padding = columnWidths[i] - data.length();
            System.out.print(" " + data);
            for (int j = 0; j < padding; j++) {
                System.out.print(" ");
            }
            System.out.print(" |");
        }
        System.out.println();
    }

    public static void showData(ContactManager contactManager){
        try {
            List<String> headers = Arrays.asList("id","Nom","Prenom","Phone1","Phone2","Address","Personal email","Por email","Gender");
            List<List<String>> data = new ArrayList<>();
            List<Contact> contactList = contactManager.getAll();
            for(Contact contact:contactList){
                data.add(Arrays.asList(String.valueOf(contact.getId()),contact.getNom(),contact.getPrenom(),contact.getTelephone1(),contact.getTelephone2(),contact.getAdresse(),contact.getPersonnel(),contact.getProfessional(),contact.getGenre()));
            }
            View.tableView(headers,data);


        }catch (DataBaseException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static boolean isContactExist(ContactManager contactManager, String fname, String lname) throws DataBaseException {
        // Checks if the contact already exist to raise an error message if it is already exist
        if (contactManager.findContactByName(fname,lname) != null){
            System.err.println("The contact already exist!");
            return true;
        }
        return false;
    }
}