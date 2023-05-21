package g_contact.utils;

import java.io.File;

public class FileManager {
    public static boolean fileExists(String filePath){
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()){
            return true;
        }
        return false;
    }
}
