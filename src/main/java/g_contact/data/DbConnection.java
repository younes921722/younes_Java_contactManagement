package g_contact.data;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection {

    private Logger logger = Logger.getLogger(getClass());

    private static String user;
    private static String password;
    private static String url;
    private static String driver;

    /** The object containing the connexion */
    private static Connection connection;




    private DbConnection() throws DataBaseException {
        try{
            Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
            url = dbProperties.getProperty("db.url");
            user = dbProperties.getProperty("db.login");
            password = dbProperties.getProperty("db.password");
            driver = dbProperties.getProperty("db.driver");

            // Load the driver of database
//            Class.forName(driver);

            // Create a connexion to database
            connection = DriverManager.getConnection(url,user,password);

        } catch (Exception ex) {
            // need to log the error
            logger.error(ex);
            // raise the exception stack
            throw new DataBaseException(ex);
        }
    }

    /**
     * returns the unique instance of connection
     *
     * @return connection
     * @throws DataBaseException
     */

    public static Connection getInstance() throws DataBaseException{
        if (connection == null){
            new DbConnection();
        }
        return connection;
    }



}
