package ntamtech.adinz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by bassiouny on 24/04/18.
 */

public class MyUtils {

    public static String getString(String item){
        if(item == null)
            item = "";
        return item;
    }
    public static String getCurrentDateTime() {

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
