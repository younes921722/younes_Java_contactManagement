package g_contact.data;

import g_contact.utils.FileManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseInstaller {

    public static void run() throws DataBaseException, SQLException{
        Connection connection = DbConnection.getInstance();

        String sql = """
                CREATE TABLE contact (
                  id bigint auto_increment,
                  nom VARCHAR(255),
                  prenom VARCHAR(255),
                  tele1 VARCHAR(255) NOT NULL,
                  tele2 VARCHAR(255) NOT NULL,
                  adresse VARCHAR(255),
                  Email_personnel VARCHAR(255) NOT NULL,
                  Email_professionnel VARCHAR(255) NOT NULL,
                  genre VARCHAR(255),
                  PRIMARY KEY (id),
                  CONSTRAINT tele1_uk UNIQUE (tele1),
                  CONSTRAINT tele2_uk UNIQUE (tele2),
                  CONSTRAINT Email_personnel_uk UNIQUE (Email_personnel),
                  CONSTRAINT Email_professionnel_uk UNIQUE (Email_professionnel)                       
                );
                """;
//        Statement stm = connection.createStatement();
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.executeUpdate();

//        stm.close();
//        connection.close();
    }
    public static boolean checkIfAlreadyInstalled() throws IOException, DataBaseException, SQLException{
        String userHomeDirectory = System.getProperty("user.home");
        Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
        String dbName = dbProperties.getProperty("db.name");
        String dataBaseFile = userHomeDirectory+"\\"+"\\h2_Db\\"+dbName+".mv.db";
        return FileManager.fileExists(dataBaseFile);
    }
}
