package g_contact.bll;

public class ExportException extends Exception {
    public ExportException(String msg, Exception ex){
        super(msg, ex);
    }
}
